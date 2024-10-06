package ru.vafeen.whisperoftasks.utils

fun Int.getTimeDefaultStr(): String = (if (this <= 9) "0" else "") + "$this"
