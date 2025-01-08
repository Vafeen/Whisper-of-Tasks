package ru.vafeen.whisperoftasks.domain.utils

fun Int.getTimeDefaultStr(): String =  "${if (this <= 9) "0" else ""}$this"
