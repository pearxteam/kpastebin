/*
 * Copyright © 2020, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.pearx.kpastebin.model

import net.pearx.kpastebin.internal.XML_PARENT_REGEX
import net.pearx.kpastebin.internal.XML_PROPERTY_REGEX

/**
 * A detailed Pastebin user information.
 * @property name Full user name.
 * @property defaultFormatShort Default paste syntax highlighting language for this user in a short format (e.g., kotlin or csharp).
 * @property defaultExpiration Default paste expiration duration for this user.
 * @property avatarUrl User avatar URL.
 * @property defaultPrivacy Default paste privacy status for this user.
 * @property website User website.
 * @property email User E-Mail.
 * @property location User location.
 * @property accountType User account type.
 */
public data class UserDetails(
    val name: String,
    val defaultFormatShort: String,
    val defaultExpiration: ExpireDate,
    val avatarUrl: String,
    val defaultPrivacy: Privacy,
    val website: String,
    val email: String,
    val location: String,
    val accountType: AccountType
) {
    internal companion object {
        fun parse(input: String): UserDetails {
            // it's a hack because currently there's no multiplatform API to parse XML with Kotlin/Native support. xmlutil doesn't support Kotlin/Native :(.
            val user = XML_PARENT_REGEX.matchEntire(input)
            if (user != null && user.groupValues[1] == "user") {
                val map = XML_PROPERTY_REGEX.findAll(user.groupValues[2]).associate { it.groupValues[1].substring(5) to it.groupValues[2] } // .substring(5) is here to cut the 'user_' part of each property.
                return UserDetails(
                    map.getValue("name"),
                    map.getValue("format_short"),
                    ExpireDate.forCode(map.getValue("expiration")),
                    map.getValue("avatar_url"),
                    Privacy.values()[map.getValue("private").toInt()],
                    map.getValue("website"),
                    map.getValue("email"),
                    map.getValue("location"),
                    AccountType.values()[map.getValue("account_type").toInt()]
                )
            }
            else
                throw IllegalArgumentException(input)
        }
    }
}
