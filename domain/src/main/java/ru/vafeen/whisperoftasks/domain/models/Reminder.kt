package ru.vafeen.whisperoftasks.domain.models

import ru.vafeen.whisperoftasks.data.duration.RepeatDuration
import java.time.LocalDate
import java.time.LocalDateTime

data class Reminder(
    val id: Int = 0,
    val idOfReminder: Int,
    val title: String,
    val text: String,
    val dt: LocalDateTime,
    val repeatDuration: RepeatDuration,
    val dateOfDone: LocalDate? = null,
    val isNotificationNeeded: Boolean = false,
)