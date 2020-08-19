package net.pearx.kpastebin

import net.pearx.kpastebin.model.Privacy

private const val BAD_API_REQUEST = "Bad API request, "
private const val POST_LIMIT = "Post limit, "

internal fun checkPastebinResponse(response: String) {
    when {
        response.startsWith(BAD_API_REQUEST) -> {
            throw when (val sub = response.substring(BAD_API_REQUEST.length)) {
                "maximum number of 25 unlisted pastes for your free account" -> FreePasteLimitException(Privacy.UNLISTED, sub)
                "maximum number of 10 private pastes for your free account" -> FreePasteLimitException(Privacy.PRIVATE, sub)
                "api_paste_code was empty" -> EmptyPasteException(sub)
                "maximum paste file size exceeded" -> PasteSizeException(sub)
                "invalid api_paste_format" -> InvalidPasteFormatException(sub)
                "invalid api_user_key" -> InvalidUserKeyException(sub)
                "invalid or expired api_user_key" -> InvalidUserKeyException(sub)
                "invalid login" -> InvalidLoginException(sub)
                "account not active" -> AccountNotActiveException(sub)
                "invalid permission to remove paste" -> InvalidPermissionException(sub)
                "invalid permission to view this paste or invalid api_paste_key" -> PasteNotFoundException(sub)
                else -> PastebinException(sub)
            }
        }
        response.startsWith(POST_LIMIT) -> {
            throw when (val sub = response.substring(POST_LIMIT.length)) {
                "maximum pastes per 24h reached" -> PastePerDayLimitException(sub)
                else -> PastebinException(sub)
            }
        }
    }
}

class InvalidUserKeyException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

class FreePasteLimitException(val privacy: Privacy, message: String, cause: Throwable? = null) : PastebinException(message, cause)
class PasteSizeException(message: String, cause: Throwable? = null) : PastebinException(message, cause)
class EmptyPasteException(message: String, cause: Throwable? = null) : PastebinException(message, cause)
class InvalidPasteFormatException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

class InvalidLoginException(message: String, cause: Throwable? = null) : PastebinException(message, cause)
class AccountNotActiveException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

class InvalidPermissionException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

class PasteNotFoundException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

class PastePerDayLimitException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

open class PastebinException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)