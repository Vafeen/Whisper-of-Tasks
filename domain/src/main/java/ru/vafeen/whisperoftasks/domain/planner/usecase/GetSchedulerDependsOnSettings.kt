package ru.vafeen.whisperoftasks.domain.planner.usecase

import org.koin.core.component.KoinComponent
import org.koin.core.qualifier.named
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager

class GetSchedulerDependsOnSettings(private val settingsManager: SettingsManager) : KoinComponent {
    operator fun invoke(): Scheduler? = settingsManager.settingsFlow.value.schedulerChoice?.let {
        getKoin().get(qualifier = named(it))
    }

}