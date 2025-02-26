package ru.vafeen.whisperoftasks.domain.planner

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

interface Scheduler {
    fun planWork(reminder: Reminder)
    fun cancelWork(reminder: Reminder)
}