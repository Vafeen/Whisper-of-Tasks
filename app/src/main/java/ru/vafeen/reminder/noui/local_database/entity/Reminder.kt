package ru.vafeen.reminder.noui.local_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val text: String,
) {
    override fun toString(): String = "$id $title $text"
}