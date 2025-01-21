package ru.vafeen.whisperoftasks.domain.planner

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

interface Scheduler {
    fun planWork(vararg reminder: Reminder)
    fun planWork(reminders: List<Reminder>)

    fun cancelWork(vararg reminder: Reminder)
    fun cancelWork(reminders: List<Reminder>)
}