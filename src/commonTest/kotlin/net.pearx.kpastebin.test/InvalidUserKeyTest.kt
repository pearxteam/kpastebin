package net.pearx.kpastebin.test

import net.pearx.kpastebin.InvalidUserKeyException
import net.pearx.kpastebin.test.helper.createClient
import net.pearx.kpastebin.test.helper.pastes
import net.pearx.kpastebin.test.helper.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidUserKeyTest {
    @Test
    fun `creating paste`() = runTest {
        assertFailsWith<InvalidUserKeyException> {
            createClient(userKey = "invalid").createPaste("TEST")
        }
    }

    @Test
    fun `listing pastes`() = runTest {
        assertFailsWith<InvalidUserKeyException> {
            createClient(userKey = "invalid").listPastes()
        }
    }

    @Test
    fun `deleting paste`() = runTest {
        assertFailsWith<InvalidUserKeyException> {
            createClient(userKey = "invalid").deletePaste(pastes.keys.first())
        }
    }

    @Test
    fun `getting user details`() = runTest {
        assertFailsWith<InvalidUserKeyException> {
            createClient(userKey = "invalid").getUserDetails()
        }
    }

    @Test
    fun `getting paste`() = runTest {
        assertFailsWith<InvalidUserKeyException> {
            createClient(userKey = "invalid").getPaste(pastes.keys.first())
        }
    }
}