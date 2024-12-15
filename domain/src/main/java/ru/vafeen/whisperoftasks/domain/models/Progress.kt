package ru.vafeen.whisperoftasks.domain.models

data class Progress(
    val totalBytesRead: Long = 0,
    val contentLength: Long = 0,
    val done: Boolean = false,
    val failed: Boolean = false,
)
