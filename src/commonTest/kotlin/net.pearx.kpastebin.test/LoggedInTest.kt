package net.pearx.kpastebin.test

import net.pearx.kpastebin.PasteNotFoundException
import net.pearx.kpastebin.model.*
import net.pearx.kpastebin.test.helper.createClient
import net.pearx.kpastebin.test.helper.globalUserKey
import net.pearx.kpastebin.test.helper.pastes
import net.pearx.kpastebin.test.helper.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LoggedInTest {
    @Test
    fun `creating paste`() = runTest {
        createClient(userKey = globalUserKey).createPaste("something")
    }

    @Test
    fun `listing pastes with invalid results limit`() = runTest {
        assertFailsWith<IllegalArgumentException> {
            createClient(userKey = globalUserKey).listPastes(resultsLimit = 0)
        }
        assertFailsWith<IllegalArgumentException> {
            createClient(userKey = globalUserKey).listPastes(resultsLimit = 1001)
        }
    }

    @Test
    fun `listing pastes`() = runTest {
        assertEquals(listOf(
            PasteDetails(
                "0b42rwhf",
                1297953260,
                "javascript test",
                15,
                1297956860,
                Privacy.PUBLIC,
                "JavaScript",
                "javascript",
                "https://pastebin.com/0b42rwhf",
                15
            ),
            PasteDetails(
                "0C343n0d",
                1297694343,
                "Welcome To Pastebin V3",
                490,
                0,
                Privacy.PUBLIC,
                "None",
                "text",
                "https://pastebin.com/0C343n0d",
                65
            )
        ), createClient(userKey = globalUserKey).listPastes())
    }

    @Test
    fun `deleting non-existing paste`() = runTest {
        assertFailsWith<PasteNotFoundException> {
            createClient(userKey = globalUserKey).deletePaste("TEST")
        }
    }


    @Test
    fun `deleting paste`() = runTest {
        createClient(userKey = globalUserKey).deletePaste(pastes.keys.first())
    }

    @Test
    fun `getting non-existing paste`() = runTest {
        assertFailsWith<PasteNotFoundException> {
            createClient(userKey = globalUserKey).getPaste("TEST")
        }
    }

    @Test
    fun `getting user details`() = runTest {
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
    fun `getting pastes`() = runTest {
        for ((key, text) in pastes) {
            assertEquals(text, createClient(userKey = globalUserKey).getPaste(key))
        }
    }
}