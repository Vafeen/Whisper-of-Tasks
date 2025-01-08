package ru.vafeen.whisperoftasks.domain.planner

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

interface Scheduler {
    fun planOneTimeWork(reminder: Reminder)
    fun planRepeatWork(reminder: Reminder)
    fun cancelWork(reminder: Reminder)
}