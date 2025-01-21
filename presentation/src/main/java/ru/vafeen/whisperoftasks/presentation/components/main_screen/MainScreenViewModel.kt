package ru.vafeen.whisperoftasks.presentation.components.main_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vafeen.whisperoftasks.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.planner.usecase.RemoveEventUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.SetEventUseCase
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager
import ru.vafeen.whisperoftasks.presentation.common.EventCreation
import java.time.LocalDate


internal class MainScreenViewModel(
    private val getAllAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase,
    private val settingsManager: SettingsManager,
    private val removeEventUseCase: RemoveEventUseCase,
    private val setEventUseCase: SetEventUseCase,
    context: Context,
) : ViewModel(), EventCreation {
    val settings = settingsManager.settingsFlow
    val todayDate: LocalDate = LocalDate.now()
    val remindersFlow = getAllAsFlowRemindersUseCase.invoke()



    override fun removeEvent(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            removeEventUseCase.invoke(reminder)
        }
    }

    override fun updateEvent(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            setEventUseCase.invoke(reminder)
        }
    }

}