package ru.vafeen.reminder.noui.local_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vafeen.reminder.noui.duration.RepeatDuration
import java.time.LocalDateTime

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idOfReminder: Int,
    val title: String,
    val text: String,
    val dt: LocalDateTime,
    val repeatDuration: RepeatDuration,
    val isDone: Boolean = false
) {


    override fun toString(): String = "$id $title $text"
}