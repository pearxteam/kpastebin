package net.pearx.kpastebin.model

import net.pearx.kpastebin.internal.XML_PARENT_REGEX
import net.pearx.kpastebin.internal.XML_PROPERTY_REGEX

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
            // it's a hack because currently there's no multiplatform API to parse XML with Kotlin/Native support
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