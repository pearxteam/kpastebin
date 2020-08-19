package net.pearx.kpastebin

import net.pearx.kpastebin.model.ExpireDate
import net.pearx.kpastebin.model.Privacy

class PastebinAccount(private val client: PastebinClient) {
    var userKey: String? = null

    private fun checkUserKeyAndReturn(): String {
        val userKey = userKey
        if(userKey == null)
            throw InvalidUserKeyException("You haven't initialized the userKey property. It can be done by setting it directly or using the 'login' function.")
        else
            return userKey
    }

    suspend fun login(username: String, password: String) {
        userKey = client.login(username, password)
    }

    suspend fun createPaste(
        text: String,
        name: String? = null,
        format: String? = null,
        privacy: Privacy? = null,
        expireDate: ExpireDate? = null
    ) = client.createPaste(text, checkUserKeyAndReturn(), name, format, privacy, expireDate)

    suspend fun listPastes(resultsLimit: Int? = null) = client.listPastes(checkUserKeyAndReturn(), resultsLimit)

    suspend fun deletePaste(pasteKey: String) = client.deletePaste(checkUserKeyAndReturn(), pasteKey)

    suspend fun getUserDetails() = client.getUserDetails(checkUserKeyAndReturn())

    suspend fun getPaste(pasteKey: String) = client.getPaste(checkUserKeyAndReturn(), pasteKey)
}