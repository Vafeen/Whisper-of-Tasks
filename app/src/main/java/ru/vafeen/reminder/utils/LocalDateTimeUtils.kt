package ru.vafeen.reminder.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId


fun LocalDateTime.toLongSeconds(): Long = atZone(ZoneId.systemDefault()).toEpochSecond()

fun LocalDate.getDateString(): String =
    "${dayOfWeek.ruDayOfWeek()}, ${dayOfMonth}." + if (month.value < 10) "0${month.value}"
    else month.value

fun LocalDateTime.getDateString(): String =
    "${dayOfWeek.ruDayOfWeek()}, ${dayOfMonth}." + if (month.value < 10) "0${month.value}"
    else month.value

