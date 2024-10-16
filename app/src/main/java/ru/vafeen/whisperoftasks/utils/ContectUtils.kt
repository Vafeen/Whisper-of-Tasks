package ru.vafeen.whisperoftasks.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.copyToClipboard(throwable: Throwable) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Error", throwable.stackTraceToString())
    clipboard.setPrimaryClip(clip)
}