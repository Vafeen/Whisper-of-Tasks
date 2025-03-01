package ru.vafeen.whisperoftasks.domain.planner.usecase

import android.util.Log
import kotlinx.coroutines.flow.first
import ru.vafeen.whisperoftasks.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager

class ChooseSchedulerUseCase(
    private val settingsManager: SettingsManager,
    private val getAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase,
    private val getSchedulerDependsOnSettings: GetSchedulerDependsOnSettings,
) {
    suspend operator fun invoke(choice: Scheduler.Companion.Choice) {
        Log.d("choice", "usecase start")
        val reminders = getAsFlowRemindersUseCase.invoke().first()
        val lastScheduler = getSchedulerDependsOnSettings.invoke()
        reminders.forEach {
            lastScheduler?.cancelWork(it)
        }
        settingsManager.save {
            it.copy(schedulerChoice = choice.value)
        }
        val newScheduler = getSchedulerDependsOnSettings.invoke()
        reminders.forEach { newScheduler?.planWork(it) }
        Log.d("choice", "usecase end")
    }
}