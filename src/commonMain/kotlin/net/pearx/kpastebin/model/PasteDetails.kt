package net.pearx.kpastebin.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement

@SerialName("paste")
@Serializable
public data class PasteDetails(
    @SerialName("paste_key")
    @XmlElement(true)
    val key: String,
    @SerialName("paste_date")
    @XmlElement(true)
    val date: Int,
    @SerialName("paste_title")
    @XmlElement(true)
    val title: String,
    @SerialName("paste_size")
    @XmlElement(true)
    val size: Int,
    @SerialName("paste_expire_date")
    @XmlElement(true)
    val expireDate: Int,
    @SerialName("paste_private")
    @XmlElement(true)
    val privacy: Privacy,
    @SerialName("paste_format_long")
    @XmlElement(true)
    val formatLong: String,
    @SerialName("paste_format_short")
    @XmlElement(true)
    val formatShort: String,
    @SerialName("paste_url")
    @XmlElement(true)
    val url: String,
    @SerialName("paste_hits")
    @XmlElement(true)
    val hist: Int
)