package ru.vafeen.whisperoftasks.domain.planner

import android.content.Intent
import ru.vafeen.whisperoftasks.domain.models.Reminder

interface EventCreator {
    suspend fun planeEvent(reminder: Reminder, intent: Intent)
    suspend fun removeEvent(reminder: Reminder, intent: Intent)
}