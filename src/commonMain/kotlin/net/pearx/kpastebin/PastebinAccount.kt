package net.pearx.kpastebin

import net.pearx.kpastebin.model.ExpireDate
import net.pearx.kpastebin.model.PasteDetails
import net.pearx.kpastebin.model.Privacy
import net.pearx.kpastebin.model.UserDetails

public class PastebinAccount(private val client: PastebinClient) {
    public var userKey: String? = null

    private fun checkUserKeyAndReturn(): String {
        val userKey = userKey
        if (userKey == null)
            throw InvalidUserKeyException("You haven't initialized the userKey property. It can be done by setting it directly or using the 'login' function.")
        else
            return userKey
    }

    public suspend fun login(username: String, password: String) {
        userKey = client.login(username, password)
    }

    public suspend fun createPaste(
        text: String,
        name: String? = null,
        format: String? = null,
        privacy: Privacy? = null,
        expireDate: ExpireDate? = null
    ): String = client.createPaste(text, checkUserKeyAndReturn(), name, format, privacy, expireDate)

    public suspend fun listPastes(resultsLimit: Int? = null): List<PasteDetails> = client.listPastes(checkUserKeyAndReturn(), resultsLimit)

    public suspend fun deletePaste(pasteKey: String) {
        client.deletePaste(checkUserKeyAndReturn(), pasteKey)
    }

    public suspend fun getUserDetails(): UserDetails = client.getUserDetails(checkUserKeyAndReturn())

    public suspend fun getPaste(pasteKey: String): String = client.getPaste(checkUserKeyAndReturn(), pasteKey)
}