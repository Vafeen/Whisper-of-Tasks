package ru.vafeen.reminder.utils

import java.time.LocalDateTime
import java.time.ZoneId


fun LocalDateTime.toLongSeconds(): Long = atZone(ZoneId.systemDefault()).toEpochSecond()
