package ru.vafeen.reminder.utils

import java.time.DayOfWeek

fun ruDaysOfWeek(): List<String> = listOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")
fun DayOfWeek.ruDayOfWeek(): String = ruDaysOfWeek()[this.value - 1]