package net.pearx.kpastebin.test.helper

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual fun runTest(f: suspend CoroutineScope.() -> Unit): dynamic = GlobalScope.promise(block = f)