package net.pearx.kpastebin.test.helper

import kotlinx.coroutines.CoroutineScope

expect fun runTest(f: suspend CoroutineScope.() -> Unit)