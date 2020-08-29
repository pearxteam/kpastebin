/*
 * Copyright © 2020, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.pearx.kpastebin

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.HttpResponseValidator
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.*
import net.pearx.kpastebin.internal.*
import net.pearx.kpastebin.model.ExpireDate
import net.pearx.kpastebin.model.PasteDetails
import net.pearx.kpastebin.model.Privacy
import net.pearx.kpastebin.model.UserDetails

/**
 * Pastebin API client with specified unique developer API key.
 * You can get your key on [official Pastebin website](https://pastebin.com/doc_api#1).
 *
 * @property devKey Unique developer API key
 * @property engine Ktor [HttpClientEngine] to use. Use null to detect it automatically.
 * @property userKey User key used for requests. Use null for guest user. It also can be set using [login] method.
 */
public class PastebinClient(
    private val devKey: String,
    private val engine: HttpClientEngine? = null,
    public var userKey: String? = null
) {
    /**
     * Constructs a new [PastebinClient] instance using specified [engine], [devKey] and [userKey].
     * @see PastebinClient
     */
    public constructor(devKey: String, engine: HttpClientEngineFactory<*>, userKey: String? = null): this(devKey, engine.create(), userKey)

    private val http: HttpClient
    init {
        val config: HttpClientConfig<*>.() -> Unit = {
            HttpResponseValidator { // https://youtrack.jetbrains.com/issue/KTOR-406
                validateResponse { response ->
                    when (response.status.value) {
                        in 300..399 -> throw RedirectResponseException(response)
                        in 400..499 -> throw ClientRequestException(response)
                        in 500..599 -> throw ServerResponseException(response)
                    }
                }
            }

            expectSuccess = false
        }
        http = if(engine == null) HttpClient(config) else HttpClient(engine, config)
    }

    private suspend fun sendRequest(url: String, userKeyRequired: Boolean, userKey: String?, parameters: Parameters): String {
        val usrKey = userKey ?: this.userKey
        if (userKeyRequired && usrKey == null)
            throw InvalidUserKeyException("The 'userKey' property is null. It can be initialized by setting it directly or using the 'login' function.")
        val out = http.post<String> {
            contentType(ContentType.Application.FormUrlEncoded)
            this.url.takeFrom(url)
            body = Parameters.build {
                append("api_dev_key", devKey)
                if (usrKey != null)
                    append("api_user_key", usrKey)
                appendAll(parameters)
            }.formUrlEncode()
        }
        checkPastebinResponse(out)
        return out
    }

    private suspend inline fun sendRequest(url: String, userKeyRequired: Boolean, userKey: String? = null, parametersBuilder: ParametersBuilder.() -> Unit) = sendRequest(url, userKeyRequired, userKey, Parameters.build(parametersBuilder))

    /**
     * Publishes a new paste with specified [text].
     * If [userKey] is null, the paste will be published anonymously.
     *
     * @param name paste title.
     * @param format paste syntax highlighting format. Please see [Pastebin documentation](https://pastebin.com/doc_api#5) for the list of available options.
     * @param privacy paste privacy status.
     * @param expireDate paste expiration duration.
     *
     * @throws InvalidUserKeyException when [userKey] is invalid or expired.
     * @throws PastePerDayLimitException when paste limit for this IP per day is exceeded.
     * @throws FreePasteLimitException when paste limit for current user is exceeded.
     * @throws PasteSizeException when [text] exceeds the size limit.
     * @throws InvalidPasteFormatException when specified [format] is invalid.
     */
    public suspend fun createPaste(
        text: String,
        name: String = "",
        format: String = "text",
        privacy: Privacy = Privacy.PUBLIC,
        expireDate: ExpireDate = ExpireDate.NEVER
    ): String {
        return sendRequest(API_URL_POST, false) {
            append("api_option", "paste")
            append("api_paste_code", text)
            append("api_paste_name", name)
            append("api_paste_format", format)
            append("api_paste_private", privacy.ordinal.toString())
            append("api_paste_expire_date", expireDate.code)
        }
    }

    /**
     * Log ins into Pastebin account using provided [username] and [password] combination and sets the [userKey] property.
     *
     * @throws InvalidLoginException when specified [username]/[password] combination is invalid.
     * @throws AccountNotActiveException when the account is not active.
     */
    public suspend fun login(username: String, password: String) {
        userKey = sendRequest(API_URL_LOGIN, false) {
            append("api_user_name", username)
            append("api_user_password", password)
        }
    }

    /**
     * Lists pastes created by this user.
     *
     * @param resultsLimit maximum number of results returned. It should be in range of 1 to 1000.
     *
     * @throws InvalidUserKeyException when [userKey] is null, expired or invalid.
     */
    public suspend fun listPastes(resultsLimit: Int = 50): List<PasteDetails> {
        require(resultsLimit in 1..1000) { "resultsLimit should be in range of 1 to 1000" }
        val out = sendRequest(API_URL_POST, true) {
            append("api_option", "list")
            append("api_results_limit", resultsLimit.toString())
        }
        if (out == "No pastes found.")
            return listOf()
        return PasteDetails.parseList(out)
    }

    /**
     * Deletes a paste by its [pasteKey] created by this user.
     *
     * @throws InvalidUserKeyException when [userKey] is null, expired or invalid.
     * @throws PasteNotFoundException when no paste with such [pasteKey] is found.
     */
    public suspend fun deletePaste(pasteKey: String) {
        val out = sendRequest(API_URL_POST, true) {
            append("api_option", "delete")
            append("api_paste_key", pasteKey)
        }
        if (out != "Paste Removed")
            throw PastebinException(out)
    }

    /**
     * Gets detailed information about this user.
     *
     * @throws InvalidUserKeyException when [userKey] is null, expired or invalid.
     */
    public suspend fun getUserDetails(): UserDetails {
        return UserDetails.parse(
            sendRequest(API_URL_POST, true) {
                append("api_option", "userdetails")
            }
        )
    }

    /**
     * Gets text of a paste by its [pasteKey].
     * If [userKey] is null, you can get only public pastes.
     *
     * @throws InvalidUserKeyException when [userKey] is expired or invalid.
     * @throws PasteNotFoundException when no paste with such [pasteKey] is found.
     */
    public suspend fun getPaste(pasteKey: String): String {
        val userKey = userKey // cache userKey because it can change
        return if (userKey == null)
            try {
                http.get { url.takeFrom("$URL_RAW/$pasteKey") }
            } catch(ex: ClientRequestException) {
                if(ex.response?.status == HttpStatusCode.NotFound)
                    throw PasteNotFoundException(ex.message, ex)
                else
                    throw ex
            }
        else
            sendRequest(API_URL_RAW, false, userKey) {
                append("api_option", "show_paste")
                append("api_paste_key", pasteKey)
            }
    }
}