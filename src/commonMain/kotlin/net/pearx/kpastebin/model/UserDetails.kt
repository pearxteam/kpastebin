package net.pearx.kpastebin.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement

@SerialName("user")
@Serializable
public data class UserDetails(
    @SerialName("user_name")
    @XmlElement(true)
    val name: String,
    @SerialName("user_format_short")
    @XmlElement(true)
    val defaultFormatShort: String,
    @SerialName("user_expiration")
    @XmlElement(true)
    val defaultExpiration: ExpireDate,
    @SerialName("user_avatar_url")
    @XmlElement(true)
    val avatarUrl: String,
    @SerialName("user_private")
    @XmlElement(true)
    val defaultPrivacy: Privacy,
    @SerialName("user_website")
    @XmlElement(true)
    val website: String,
    @SerialName("user_email")
    @XmlElement(true)
    val email: String,
    @SerialName("user_location")
    @XmlElement(true)
    val location: String,
    @SerialName("user_account_type")
    @XmlElement(true)
    val accountType: AccountType
)