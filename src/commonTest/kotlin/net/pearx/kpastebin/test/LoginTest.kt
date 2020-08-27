/*
 * Copyright © 2020, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

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
