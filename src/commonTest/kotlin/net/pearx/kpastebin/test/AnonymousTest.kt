/*
 * Copyright © 2020, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.pearx.kpastebin.test

import net.pearx.kpastebin.*
import net.pearx.kpastebin.model.ExpireDate
import net.pearx.kpastebin.model.Privacy
import net.pearx.kpastebin.test.helper.createClient
import net.pearx.kpastebin.test.helper.lastCreatedPaste
import net.pearx.kpastebin.test.helper.pastes
import net.pearx.kpastebin.test.helper.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AnonymousTest {
    @Test
    fun gettingNonExistingPasteFailsWith404() = runTest {
        assertFailsWith<PasteNotFoundException> {
            createClient("").getPaste("123")
        }
    }

    @Test
    fun gettingPaste() = runTest {
        val cl = createClient("")
        for ((pasteKey, pasteCode) in pastes) {
            assertEquals(pasteCode, cl.getPaste(pasteKey))
        }
    }

    @Test
    fun creatingPasteWithInvalidDevKeyFails() = runTest {
        assertFailsWith<InvalidDevKeyException> {
            createClient("").createPaste("SoMeThInG")
        }
    }

    @Test
    fun creatingPasteWithEmptyBody() = runTest {
        assertFailsWith<EmptyPasteException> {
            createClient().createPaste("")
        }
    }

    @Test
    fun creatingPasteWithInvalidFormat() = runTest {
        assertFailsWith<InvalidPasteFormatException> {
            createClient().createPaste("test", format = "notatext")
        }
    }

    @Test
    fun creatingPasteWithExceedingSizeLimit() = runTest {
        assertFailsWith<PasteSizeException> {
            createClient().createPaste("a".repeat(2048))
        }
    }

    @Test
    fun creatingPaste() = runTest {
        val text = """
            class Main {
                public static void main() {
                    System.out.println("hello world");
                }
            }
        """.trimIndent()
        val title = "HELLO WORLD!"
        createClient().createPaste(text, title, "java", Privacy.UNLISTED, ExpireDate.ONE_WEEK)
        assertEquals(text, lastCreatedPaste["code"])
        assertEquals(title, lastCreatedPaste["name"])
        assertEquals("java", lastCreatedPaste["format"])
        assertEquals(ExpireDate.ONE_WEEK.code, lastCreatedPaste["expireDate"])
        assertEquals(Privacy.UNLISTED.ordinal.toString(), lastCreatedPaste["private"])
    }
}
