package net.pearx.kpastebin.model

import net.pearx.kpastebin.internal.XML_PARENT_REGEX
import net.pearx.kpastebin.internal.XML_PROPERTY_REGEX

public data class PasteDetails(
    val key: String,
    val date: Int,
    val title: String,
    val size: Int,
    val expireDate: Int,
    val privacy: Privacy,
    val formatLong: String,
    val formatShort: String,
    val url: String,
    val hist: Int
) {
    internal companion object {
        fun parseList(input: String): List<PasteDetails> {
            val lst = mutableListOf<PasteDetails>()
            // it's a hack because currently there's no multiplatform API to parse XML with Kotlin/Native support
            for (paste in XML_PARENT_REGEX.findAll(input)) {
                if (paste.groupValues[1] == "paste") {
                    val map = XML_PROPERTY_REGEX.findAll(paste.groupValues[2]).associate { it.groupValues[1].substring(6) to it.groupValues[2] } // .substring(6) is here to cut the 'paste_' part of each property.
                    lst.add(PasteDetails(
                        map.getValue("key"),
                        map.getValue("date").toInt(),
                        map.getValue("title"),
                        map.getValue("size").toInt(),
                        map.getValue("expire_date").toInt(),
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