package ru.vafeen.whisperoftasks.utils

import android.content.Context
import ru.vafeen.whisperoftasks.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

private val daysOfWeek = listOf(
    R.string.monday,
    R.string.tuesday,
    R.string.wednesday,
    R.string.thursday,
    R.string.friday,
    R.string.satudray,
    R.string.sunday
)

operator fun LocalTime.compareTo(localDateTime: LocalDateTime): Int {
    return this.compareTo(LocalTime.of(localDateTime.hour, localDateTime.minute))
}

fun LocalDate.getDateString(): String = "${dayOfMonth}." + if (month.value < 10) "0${month.value}"
else month.value

fun LocalDateTime.getDateStringWithWeekOfDay(context: Context): String =
    "${context.getString(daysOfWeek[dayOfWeek.value - 1])}, ${dayOfMonth}." + if (month.value < 10) "0${month.value}"
    else month.value

fun LocalDate.getDateStringWithWeekOfDay(context: Context): String =
    "${context.getString(daysOfWeek[dayOfWeek.value - 1])}, ${dayOfMonth}." + if (month.value < 10) "0${month.value}"
    else month.value

fun LocalDateTime.getDateString(): String =
    "${dayOfMonth}." + if (month.value < 10) "0${month.value}"
    else month.value

fun localTimeNowHHMM(): LocalTime = LocalTime.now().withSecond(0).withNano(0)

fun LocalDateTime.withTime(localTime: LocalTime): LocalDateTime =
    this.withHour(localTime.hour).withMinute(localTime.minute)

fun LocalDateTime.withDate(localDate: LocalDate): LocalDateTime =
    this.withDayOfMonth(localDate.dayOfMonth).withMonth(localDate.monthValue)
        .withYear(localDate.year)
