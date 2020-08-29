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
        val pastes = createClient(userKey = globalUserKey).listPastes()
        assertEquals(2, pastes.size)
        pastes[0].apply {
            assertEquals("0b42rwhf", key)
            assertEquals(1297953260UL, date)
            assertEquals("javascript test", title)
            assertEquals(15, size)
            assertEquals(1297956860UL, expireDate)
            assertEquals(Privacy.PUBLIC, privacy)
            assertEquals("JavaScript", formatLong)
            assertEquals("javascript", formatShort)
            assertEquals("https://pastebin.com/0b42rwhf", url)
            assertEquals(15, hits)
        }
        pastes[1].apply {
            assertEquals("0C343n0d", key)
            assertEquals(1297694343UL, date)
            assertEquals("Welcome To Pastebin V3", title)
            assertEquals(490, size)
            assertEquals(0UL, expireDate)
            assertEquals(Privacy.PUBLIC, privacy)
            assertEquals("None", formatLong)
            assertEquals("text", formatShort)
            assertEquals("https://pastebin.com/0C343n0d", url)
            assertEquals(65, hits)
        }
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
        createClient(userKey = globalUserKey).getUserDetails().apply {
            assertEquals("wiz_kitty", name)
            assertEquals("text", defaultFormatShort)
            assertEquals(ExpireDate.NEVER, defaultExpiration)
            assertEquals("https://pastebin.com/cache/a/1.jpg", avatarUrl)
            assertEquals(Privacy.UNLISTED, defaultPrivacy)
            assertEquals("https://myawesomesite.com", website)
            assertEquals("oh@dear.com", email)
            assertEquals("New York", location)
            assertEquals(AccountType.PRO, accountType)
        }
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
