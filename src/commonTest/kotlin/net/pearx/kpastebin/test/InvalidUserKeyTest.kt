/*
 * Copyright © 2020, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.pearx.kpastebin.test

import net.pearx.kpastebin.InvalidUserKeyException
import net.pearx.kpastebin.test.helper.createClient
import net.pearx.kpastebin.test.helper.pastes
import net.pearx.kpastebin.test.helper.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidUserKeyTest {
    @Test
    fun creatingPaste() = runTest {
        assertFailsWith<InvalidUserKeyException> {
            createClient(userKey = "invalid").createPaste("TEST")
        }
    }

    @Test
    fun listingPastes() = runTest {
        assertFailsWith<InvalidUserKeyException> {
            createClient(userKey = "invalid").listPastes()
        }
    }

    @Test
    fun deletingPaste() = runTest {
        assertFailsWith<InvalidUserKeyException> {
            createClient(userKey = "invalid").deletePaste(pastes.keys.first())
        }
    }

    @Test
    fun gettingUserDetails() = runTest {
        assertFailsWith<InvalidUserKeyException> {
            createClient(userKey = "invalid").getUserDetails()
        }
    }

    @Test
    fun gettingPaste() = runTest {
        assertFailsWith<InvalidUserKeyException> {
            createClient(userKey = "invalid").getPaste(pastes.keys.first())
        }
    }
}
