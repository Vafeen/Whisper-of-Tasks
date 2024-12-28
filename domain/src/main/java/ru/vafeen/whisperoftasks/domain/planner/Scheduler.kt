package ru.vafeen.whisperoftasks.domain.planner

import android.content.Intent
import ru.vafeen.whisperoftasks.domain.models.Reminder

interface Scheduler {
    fun planOneTimeWork(reminder: Reminder, intent: Intent)
    fun planRepeatWork(reminder: Reminder, intent: Intent)
    fun cancelWork(reminder: Reminder, intent: Intent)
}