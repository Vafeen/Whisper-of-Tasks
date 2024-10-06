package ru.vafeen.whisperoftasks.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

operator fun LocalTime.compareTo(localDateTime: LocalDateTime): Int {
    return this.compareTo(LocalTime.of(localDateTime.hour, localDateTime.minute))
}

fun LocalDate.getDateString(): String = "${dayOfMonth}." + if (month.value < 10) "0${month.value}"
else month.value

fun LocalDateTime.getDateString(): String =
    "${dayOfMonth}." + if (month.value < 10) "0${month.value}"
    else month.value

fun LocalDate.getDateStringWithDayOfWeek(ruDaysOfWeek: List<String>): String =
    "${dayOfWeek.ruDayOfWeek(ruDaysOfWeek = ruDaysOfWeek)}, ${dayOfMonth}." + if (month.value < 10) "0${month.value}"
    else month.value




