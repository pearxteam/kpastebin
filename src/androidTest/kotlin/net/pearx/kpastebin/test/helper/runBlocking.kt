package net.pearx.kpastebin.test.helper

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

actual fun runTest(f: suspend CoroutineScope.() -> Unit) = runBlocking(block = f)