package net.pearx.kpastebin

import io.ktor.client.request.get
import io.ktor.http.takeFrom
import net.pearx.kpastebin.internal.Http
import net.pearx.kpastebin.internal.URL_RAW

public suspend fun getPaste(pasteKey: String): String? {
    return Http.get<String> { url.takeFrom("$URL_RAW/$pasteKey") }
}