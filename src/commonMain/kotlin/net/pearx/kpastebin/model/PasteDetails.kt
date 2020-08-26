package net.pearx.kpastebin.model

import net.pearx.kpastebin.internal.XML_PARENT_REGEX
import net.pearx.kpastebin.internal.XML_PROPERTY_REGEX

/**
 * A detailed Pastebin paste information.
 */
@ExperimentalUnsignedTypes
public data class PasteDetails(
    /** Paste key. */
    val key: String,
    /** Paste publication date. */
    val date: Int,
    /** Paste title. */
    val title: String,
    /** Paste size in bytes. */
    val size: Int,
    /** Paste expiration date in Unix time format. */
    val expireDate: ULong,
    /** Paste privacy status. */
    val privacy: Privacy,
    /** Paste syntax highlighting language in a user-readable format (e.g., Kotlin or C#). */
    val formatLong: String,
    /** Paste syntax highlighting language in a short format (e.g. kotlin or csharp). */
    val formatShort: String,
    /** Paste URL. */
    val url: String,
    /** Paste view count. */
    val hits: Int
) {
    internal companion object {
        fun parseList(input: String): List<PasteDetails> {
            val lst = mutableListOf<PasteDetails>()
            // it's a hack because currently there's no multiplatform API to parse XML with Kotlin/Native support. xmlutil doesn't support Kotlin/Native :(.
            for (paste in XML_PARENT_REGEX.findAll(input)) {
                if (paste.groupValues[1] == "paste") {
                    val map = XML_PROPERTY_REGEX.findAll(paste.groupValues[2]).associate { it.groupValues[1].substring(6) to it.groupValues[2] } // .substring(6) is here to cut the 'paste_' part of each property.
                    lst.add(PasteDetails(
                        map.getValue("key"),
                        map.getValue("date").toInt(),
                        map.getValue("title"),
                        map.getValue("size").toInt(),
                        map.getValue("expire_date").toULong(),
                        Privacy.values()[map.getValue("private").toInt()],
                        map.getValue("format_long"),
                        map.getValue("format_short"),
                        map.getValue("url"),
                        map.getValue("hits").toInt()
                    ))
                }
                else
                    throw IllegalArgumentException(input)
            }
            return lst
        }
    }
}