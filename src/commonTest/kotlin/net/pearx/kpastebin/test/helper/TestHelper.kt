/*
 * Copyright © 2020, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.pearx.kpastebin.test.helper

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.toByteArray
import io.ktor.http.*
import io.ktor.utils.io.core.toByteArray
import net.pearx.kpastebin.PastebinClient

val pastes = mapOf(
    "javahelloworld" to """
        // Your First Program

        class HelloWorld {
            public static void main(String[] args) {
                System.out.println("Hello, World!"); 
            }
        }
    """.trimIndent(),
    "sometext" to """
        SOME TEXT 123123123 123123123
    """.trimIndent()
)

const val globalDevKey = "devkey123123123"
val credentials = "testuser" to "testpassword"
val inactiveCredentials = "inactiveuser" to "fjasdjfasdj"
const val emptyPastesUserKey = "emptyKey"
const val globalUserKey = "KeYkEyKeY"

lateinit var lastCreatedPaste: Map<String, String?>

fun createClient(devKey: String = globalDevKey, userKey: String? = null) = PastebinClient(createMockEngine(), devKey, userKey)

fun createMockEngine() = MockEngine.create {
        addHandler { request ->
            val url = request.url
            if (url.protocol == URLProtocol.HTTPS && url.host == "pastebin.com") {
                val headers = headersOf("Content-Type", ContentType.Text.Plain.toString())
                val path = url.encodedPath
                when {
                    path.startsWith("/raw/") -> {
                        val paste = path.substring(5)
                        val text = pastes[paste]
                        if (text != null)
                            respond(text, headers = headers)
                        else
                            respond("404", HttpStatusCode.NotFound, headers)
                    }
                    path.startsWith("/api/") -> {
                        if (request.method != HttpMethod.Post)
                            respond("Bad API request, use POST request, not GET", headers = headers)
                        else {
                            val data = request.body.toByteArray().decodeToString().parseUrlEncodedParameters()
                            if (globalDevKey != data["api_dev_key"])
                                respond("Bad API request, invalid api_dev_key", headers = headers)
                            else {
                                when (path) {
                                    "/api/api_login.php" -> {
                                        val username = data["api_user_name"]!!
                                        val password = data["api_user_password"]!!
                                        when {
                                            username == inactiveCredentials.first && password == inactiveCredentials.second -> respond("Bad API request, account not active", headers = headers)
                                            username == credentials.first && password == credentials.second -> respond(globalUserKey, headers = headers)
                                            else -> respond("Bad API request, invalid login", headers = headers)
                                        }
                                    }
                                    "/api/api_raw.php" -> {
                                        val userKey = data["api_user_key"]!!
                                        val pasteKey = data["api_paste_key"]!!
                                        val option = data["api_option"]!!
                                        when {
                                            userKey != globalUserKey -> respond("Bad API request, invalid api_user_key", headers = headers)
                                            option != "show_paste" -> respond("Bad API request, invalid api_option", headers = headers)
                                            else -> {
                                                val pasteCode = pastes[pasteKey]
                                                if (pasteCode == null)
                                                    respond("Bad API request, invalid permission to view this paste or invalid api_paste_key")
                                                else
                                                    respond(pasteCode)
                                            }
                                        }
                                    }
                                    "/api/api_post.php" -> {
                                        val option = data["api_option"]
                                        when (option) {
                                            "paste" -> {
                                                val pasteCode = data["api_paste_code"]!!.decodeURLQueryComponent(plusIsSpace = true)
                                                val userKey = if ("api_user_key" in data) data["api_user_key"] else null
                                                val pasteName = data["api_paste_name"]!!.decodeURLQueryComponent(plusIsSpace = true)
                                                val pasteFormat = data["api_paste_format"]!!
                                                val pastePrivate = data["api_paste_private"]!!.toInt()
                                                val pasteExpireDate = data["api_paste_expire_date"]!!
                                                when {
                                                    pasteExpireDate !in setOf("N", "10M", "1H", "1D", "1W", "2W", "1M", "6M", "1Y") -> respond("Bad API request, invalid api_paste_expire_date", headers = headers)
                                                    pastePrivate !in 0..2 -> respond("Bad API request, invalid api_paste_private", headers = headers)
                                                    pasteFormat !in setOf("java", "kotlin", "text") -> respond("Bad API request, invalid api_paste_format", headers = headers)
                                                    userKey != null && userKey != globalUserKey -> respond("Bad API request, invalid api_user_key", headers = headers)
                                                    pasteCode.toByteArray().size >= 1024 -> respond("Bad API request, maximum paste file size exceeded", headers = headers)
                                                    pasteCode.isEmpty() -> respond("Bad API request, api_paste_code was empty", headers = headers)
                                                    else -> {
                                                        lastCreatedPaste = mapOf(
                                                            "code" to pasteCode,
                                                            "userKey" to userKey,
                                                            "name" to pasteName,
                                                            "format" to pasteFormat,
                                                            "private" to pastePrivate.toString(),
                                                            "expireDate" to pasteExpireDate
                                                        )
                                                        respond("https://pastebin.com/lastCreated", headers = headers)
                                                    }
                                                }
                                            }
                                            "list" -> {
                                                val userKey = data["api_user_key"]!!
                                                val resultsLimit = data["api_results_limit"]!!.toInt()
                                                when {
                                                    userKey !in setOf(globalUserKey, emptyPastesUserKey) -> respond("Bad API request, invalid api_user_key", headers = headers)
                                                    resultsLimit !in 1..1000 -> respond("Bad API request, invalid api_results_limit", headers = headers)
                                                    userKey == emptyPastesUserKey -> respond("No pastes found.", headers = headers)
                                                    else -> respond("""
                                                            <paste>
                                                                    <paste_key>0b42rwhf</paste_key>
                                                                    <paste_date>1297953260</paste_date>
                                                                    <paste_title>javascript test</paste_title>
                                                                    <paste_size>15</paste_size>
                                                                    <paste_expire_date>1297956860</paste_expire_date>
                                                                    <paste_private>0</paste_private>
                                                                    <paste_format_long>JavaScript</paste_format_long>
                                                                    <paste_format_short>javascript</paste_format_short>
                                                                    <paste_url>https://pastebin.com/0b42rwhf</paste_url>
                                                                    <paste_hits>15</paste_hits>
                                                            </paste>
                                                            <paste>
                                                                    <paste_key>0C343n0d</paste_key>
                                                                    <paste_date>1297694343</paste_date>
                                                                    <paste_title>Welcome To Pastebin V3</paste_title>
                                                                    <paste_size>490</paste_size>
                                                                    <paste_expire_date>0</paste_expire_date>
                                                                    <paste_private>0</paste_private>
                                                                    <paste_format_long>None</paste_format_long>
                                                                    <paste_format_short>text</paste_format_short>
                                                                    <paste_url>https://pastebin.com/0C343n0d</paste_url>
                                                                    <paste_hits>65</paste_hits>
                                                            </paste>
                                                        """.trimIndent())
                                                }
                                            }
                                            "delete" -> {
                                                val userKey = data["api_user_key"]!!
                                                val pasteKey = data["api_paste_key"]!!
                                                when {
                                                    userKey != globalUserKey -> respond("Bad API request, invalid or expired api_user_key", headers = headers)
                                                    pasteKey !in pastes.keys -> respond("Bad API request, invalid permission to remove paste", headers = headers)
                                                    else -> respond("Paste Removed", headers = headers)
                                                }
                                            }
                                            "userdetails" -> {
                                                val userKey = data["api_user_key"]!!
                                                when {
                                                    userKey != globalUserKey -> respond("Bad API request, invalid api_user_key", headers = headers)
                                                    else -> respond("""
                                                            <user>
                                                                    <user_name>wiz_kitty</user_name>
                                                                    <user_format_short>text</user_format_short>
                                                                    <user_expiration>N</user_expiration>
                                                                    <user_avatar_url>https://pastebin.com/cache/a/1.jpg</user_avatar_url>
                                                                    <user_private>1</user_private>
                                                                    <user_website>https://myawesomesite.com</user_website>
                                                                    <user_email>oh@dear.com</user_email>
                                                                    <user_location>New York</user_location>
                                                                    <user_account_type>1</user_account_type>
                                                            </user>
                                                        """.trimIndent(), headers = headers)
                                                }
                                            }
                                            else -> respond("Bad API request, invalid api_option", headers = headers)
                                        }
                                    }
                                    else -> respond("404", HttpStatusCode.NotFound, headers)

                                }
                            }
                        }
                    }
                    else -> respond("404", HttpStatusCode.NotFound, headers)
                }
            }
            else {
                error("Protocol and host isn't https://pastebin.com/: $url!")
            }
        }
    }