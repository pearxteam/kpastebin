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
    fun `getting non-existing paste fails with 404`() = runTest {
        assertFailsWith<PasteNotFoundException> {
            createClient("").getPaste("123")
        }
    }

    @Test
    fun `getting paste`() = runTest {
        val cl = createClient("")
        for ((pasteKey, pasteCode) in pastes) {
            assertEquals(pasteCode, cl.getPaste(pasteKey))
        }
    }

    @Test
    fun `creating paste with invalid devKey fails`() = runTest {
        assertFailsWith<InvalidDevKeyException> {
            createClient("").createPaste("SoMeThInG")
        }
    }

    @Test
    fun `creating paste with empty body`() = runTest {
        assertFailsWith<EmptyPasteException> {
            createClient().createPaste("")
        }
    }

    @Test
    fun `creating paste with invalid format`() = runTest {
        assertFailsWith<InvalidPasteFormatException> {
            createClient().createPaste("test", format = "notatext")
        }
    }

    @Test
    fun `creating paste with exceeding size limit`() = runTest {
        assertFailsWith<PasteSizeException> {
            createClient().createPaste("a".repeat(2048))
        }
    }

    @Test
    fun `creating paste`() = runTest {
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