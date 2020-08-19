package net.pearx.kpastebin

import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.request.post
import io.ktor.http.*
import net.pearx.kpastebin.internal.API_URL_LOGIN
import net.pearx.kpastebin.internal.API_URL_POST
import net.pearx.kpastebin.internal.API_URL_RAW
import net.pearx.kpastebin.internal.checkPastebinResponse
import net.pearx.kpastebin.model.ExpireDate
import net.pearx.kpastebin.model.PasteDetails
import net.pearx.kpastebin.model.Privacy
import net.pearx.kpastebin.model.UserDetails

public class PastebinClient(private val devKey: String) {
    private val http = HttpClient {
        defaultRequest {
            contentType(ContentType.Application.FormUrlEncoded)
        }
    }

    private suspend fun sendRequest(url: String, parameters: Parameters): String {
        val out = http.post<String> {
            this.url.takeFrom(url)
            body = Parameters.build {
                append("api_dev_key", devKey)
                appendAll(parameters)
            }.formUrlEncode()
        }
        checkPastebinResponse(out)
        return out
    }

    private suspend inline fun sendRequest(url: String, parametersBuilder: ParametersBuilder.() -> Unit) = sendRequest(url, Parameters.build(parametersBuilder))

    public suspend fun createPaste(
        text: String,
        userKey: String? = null,
        name: String? = null,
        format: String? = null,
        privacy: Privacy? = null,
        expireDate: ExpireDate? = null
    ): String {
        return sendRequest(API_URL_POST) {
            append("api_option", "paste")
            append("api_paste_code", text)
            if (userKey != null)
                append("api_user_key", userKey)
            if (name != null)
                append("api_paste_name", name)
            if (format != null)
                append("api_paste_format", format)
            if (privacy != null)
                append("api_privacy", privacy.ordinal.toString())
            if (expireDate != null)
                append("api_paste_expire_date", expireDate.code)
        }
    }

    public suspend fun login(username: String, password: String): String {
        return sendRequest(API_URL_LOGIN) {
            append("api_user_name", username)
            append("api_user_password", password)
        }
    }

    public suspend fun listPastes(userKey: String, resultsLimit: Int? = null): List<PasteDetails> {
        val out = sendRequest(API_URL_POST) {
            append("api_user_key", userKey)
            append("api_option", "list")
            if (resultsLimit != null)
                append("api_results_limit", resultsLimit.toString())
        }
        if (out == "No pastes found.")
            return listOf()
        return PasteDetails.parseList(out)
    }

    public suspend fun deletePaste(userKey: String, pasteKey: String) {
        val out = sendRequest(API_URL_POST) {
            append("api_option", "delete")
            append("api_user_key", userKey)
            append("api_paste_key", pasteKey)
        }
        if (out != "Paste Removed")
            throw PastebinException(out)
    }

    public suspend fun getUserDetails(userKey: String): UserDetails {
        return UserDetails.parse(
            sendRequest(API_URL_POST) {
                append("api_option", "userdetails")
                append("api_user_key", userKey)
            }
        )
    }

    public suspend fun getPaste(userKey: String, pasteKey: String): String {
        return sendRequest(API_URL_RAW) {
            append("api_option", "show_paste")
            append("api_user_key", userKey)
            append("api_paste_key", pasteKey)
        }
    }

//
//    suspend fun getPaste(pasteKey: String): String? {
//        return http.get<String> { url.takeFrom("$URL_RAW/$pasteKey") }
//    }
}