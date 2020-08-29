/*
 * Copyright © 2020, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.pearx.kpastebin.test

import net.pearx.kpastebin.PasteNotFoundException
import net.pearx.kpastebin.model.*
import net.pearx.kpastebin.test.helper.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LoggedInTest {
    @Test
    fun creatingPaste() = runTest {
        createClient(userKey = globalUserKey).createPaste("something")
    }

    @Test
    fun listingPastesWithInvalidResultsLimit() = runTest {
        assertFailsWith<IllegalArgumentException> {
            createClient(userKey = globalUserKey).listPastes(resultsLimit = 0)
        }
        assertFailsWith<IllegalArgumentException> {
            createClient(userKey = globalUserKey).listPastes(resultsLimit = 1001)
        }
    }

    @Test
    fun listingPastes() = runTest {
        assertEquals(listOf(
            PasteDetails(
                "0b42rwhf",
                1297953260UL,
                "javascript test",
                15,
                1297956860UL,
                Privacy.PUBLIC,
                "JavaScript",
                "javascript",
                "https://pastebin.com/0b42rwhf",
                15
            ),
            PasteDetails(
                "0C343n0d",
                1297694343UL,
                "Welcome To Pastebin V3",
                490,
                0UL,
                Privacy.PUBLIC,
                "None",
                "text",
                "https://pastebin.com/0C343n0d",
                65
            )
        ), createClient(userKey = globalUserKey).listPastes())
    }

    @Test
    fun deletingNonExistingPaste() = runTest {
        assertFailsWith<PasteNotFoundException> {
            createClient(userKey = globalUserKey).deletePaste("TEST")
        }
    }


    @Test
    fun deletingPaste() = runTest {
        createClient(userKey = globalUserKey).deletePaste(pastes.keys.first())
    }

    @Test
    fun gettingNonExistingPaste() = runTest {
        assertFailsWith<PasteNotFoundException> {
            createClient(userKey = globalUserKey).getPaste("TEST")
        }
    }

    @Test
    fun gettingUserDetails() = runTest {
        assertEquals(UserDetails(
            "wiz_kitty",
            "text",
            ExpireDate.NEVER,
            "https://pastebin.com/cache/a/1.jpg",
            Privacy.UNLISTED,
            "https://myawesomesite.com",
            "oh@dear.com",
            "New York",
            AccountType.PRO
        ), createClient(userKey = globalUserKey).getUserDetails())
    }

    @Test
    fun gettingPastes() = runTest {
        for ((key, text) in pastes) {
            assertEquals(text, createClient(userKey = globalUserKey).getPaste(key))
        }
    }

    @Test
    fun gettingPastesEmpty() = runTest {
        assertEquals(listOf(), createClient(userKey = emptyPastesUserKey).listPastes())
    }
}
