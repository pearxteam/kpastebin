package net.pearx.kpastebin

import io.ktor.client.request.get
import io.ktor.http.takeFrom
import net.pearx.kpastebin.internal.Http
import net.pearx.kpastebin.internal.URL_RAW

/**
 * Gets Pastebin paste text by its [pasteKey] anonymously.
 *
 * @see PastebinClient.getPaste
 */
public suspend fun getPaste(pasteKey: String): String? {
    return Http.get<String> { url.takeFrom("$URL_RAW/$pasteKey") }
}