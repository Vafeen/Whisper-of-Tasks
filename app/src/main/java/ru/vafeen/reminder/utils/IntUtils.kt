package ru.vafeen.reminder.utils

fun Int.getTimeDefaultStr(): String = (if (this <= 9) "0" else "") + "$this"
