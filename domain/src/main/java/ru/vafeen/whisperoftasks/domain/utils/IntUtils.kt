package ru.vafeen.whisperoftasks.domain.utils

import android.content.Context

fun Int.getTimeDefaultStr(): String = "${if (this <= 9) "0" else ""}$this"
fun Int.pixelsToDp(context: Context): Float =
    this / (context.resources.displayMetrics.densityDpi / 160f)
