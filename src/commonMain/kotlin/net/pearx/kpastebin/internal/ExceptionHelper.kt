/*
 * Copyright © 2020, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.pearx.kpastebin.internal

import net.pearx.kpastebin.*
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
                "invalid permission to remove paste" -> PasteNotFoundException(sub)
                "invalid permission to view this paste or invalid api_paste_key" -> PasteNotFoundException(sub)
                "invalid api_dev_key" -> InvalidDevKeyException(sub)
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
