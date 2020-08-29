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
 * A detailed Pastebin paste information.
 * @property key Paste key.
 * @property date Paste publication date in Unix time format.
 * @property title Paste title.
 * @property size Paste size in bytes.
 * @property expireDate Paste expiration date in Unix time format.
 * @property privacy Paste privacy status.
 * @property formatLong Paste syntax highlighting language in a user-readable format (e.g., Kotlin or C#).
 * @property formatShort Paste syntax highlighting language in a short format (e.g. kotlin or csharp).
 * @property url Paste URL.
 * @property hits Paste view count.
 */
public data class PasteDetails(
    val key: String,
    val date: ULong,
    val title: String,
    val size: Int,
    val expireDate: ULong,
    val privacy: Privacy,
    val formatLong: String,
    val formatShort: String,
    val url: String,
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
                        map.getValue("date").toULong(),
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
