package ru.vafeen.whisperoftasks.domain.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun CoroutineScope.launchMain(block: suspend CoroutineScope.() -> Unit) =
    launch(Dispatchers.Main, block = block)

fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) =
    launch(Dispatchers.IO, block = block)

suspend fun withContextIO(block: suspend CoroutineScope.() -> Unit) =
    withContext(Dispatchers.IO, block = block)

suspend fun withContextMain(block: suspend CoroutineScope.() -> Unit) =
    withContext(Dispatchers.Main, block = block)