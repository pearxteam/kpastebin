package net.pearx.kpastebin.internal

internal const val API_URL = "https://pastebin.com/api"
internal const val API_URL_POST = "$API_URL/api_post.php"
internal const val API_URL_LOGIN = "$API_URL/api_login.php"
internal const val API_URL_RAW = "$API_URL/api_raw.php"
internal const val URL_RAW = "https://pastebin.com/raw"

internal val XML_PROPERTY_REGEX = Regex("<([a-zA-Z_]+)>(.*)</\\1>")
internal val XML_PARENT_REGEX = Regex("<([a-zA-Z]+)>((?:.|\r\n|\r|\n)+?)</\\1>")