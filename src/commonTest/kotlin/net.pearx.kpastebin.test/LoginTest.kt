package net.pearx.kpastebin.test

import net.pearx.kpastebin.AccountNotActiveException
import net.pearx.kpastebin.InvalidLoginException
import net.pearx.kpastebin.test.helper.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LoginTest {
    @Test
    fun `logging in with inactive credentials`() = runTest {
        assertFailsWith<AccountNotActiveException> {
            createClient().login(inactiveCredentials.first, inactiveCredentials.second)
        }
    }

    @Test
    fun `logging in with invalid credentials`() = runTest {
        assertFailsWith<InvalidLoginException> {
            createClient().login("ABC", "DEF")
        }
    }

    @Test
    fun `logging in`() = runTest {
        val cl = createClient()
        cl.login(credentials.first, credentials.second)
        assertEquals(globalUserKey, cl.userKey)
    }
}