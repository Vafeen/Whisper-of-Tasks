package ru.vafeen.whisperoftasks.data.utils

import android.content.Context

fun Int.getTimeDefaultStr(): String =  "${if (this <= 9) "0" else ""}$this"

fun Int.pixelsToDp(context: Context): Float {
    val densityDpi = context.resources.displayMetrics.densityDpi
    return this / (densityDpi / 160f)
}