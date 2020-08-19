package net.pearx.kpastebin.model

import net.pearx.kpastebin.internal.XML_PARENT_REGEX
import net.pearx.kpastebin.internal.XML_PROPERTY_REGEX

/**
 * A detailed Pastebin user information
 */
public data class UserDetails(
    /** Full user name */
    val name: String,
    /** Default paste syntax highlighting language for this user in a short format (e.g., kotlin or csharp) */
    val defaultFormatShort: String,
    /** Default paste expiration duration for this user */
    val defaultExpiration: ExpireDate,
    /** User avatar URL */
    val avatarUrl: String,
    /** Default paste privacy status for this user */
    val defaultPrivacy: Privacy,
    /** User website */
    val website: String,
    /** User E-Mail */
    val email: String,
    /** User location */
    val location: String,
    /** User account type */
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