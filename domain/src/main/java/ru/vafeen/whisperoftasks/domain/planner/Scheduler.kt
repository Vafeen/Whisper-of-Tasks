package ru.vafeen.whisperoftasks.domain.planner

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

interface Scheduler {
    fun planWork(reminder: Reminder)
    fun cancelWork(reminder: Reminder)

    companion object {
        enum class Choice(val value: String) {
            WORK_MANAGER("WORK_MANAGER"),
            ALARM_MANAGER("ALARM_MANAGER");

            companion object {
                val all = listOf(WORK_MANAGER, ALARM_MANAGER)
            }
        }
    }
}

