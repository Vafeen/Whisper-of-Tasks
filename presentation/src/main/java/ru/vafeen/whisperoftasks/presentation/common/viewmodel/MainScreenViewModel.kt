package ru.vafeen.whisperoftasks.presentation.common.viewmodel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vafeen.whisperoftasks.domain.models.Reminder
import ru.vafeen.whisperoftasks.domain.network.Downloader
import ru.vafeen.whisperoftasks.domain.planner.EventCreation
import ru.vafeen.whisperoftasks.domain.planner.EventCreator
import ru.vafeen.whisperoftasks.domain.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.whisperoftasks.presentation.NotificationReminderReceiver
import java.time.LocalDate


internal class MainScreenViewModel(
    private val getAllAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase,
    private val sharedPreferences: SharedPreferences,
    private val eventCreator: EventCreator,
    private val downloader: Downloader,
    context: Context,
) : ViewModel(), EventCreation {
    private val _settings =
        MutableStateFlow(sharedPreferences.getSettingsOrCreateIfNull())
    val settings = _settings.asStateFlow()
    val todayDate: LocalDate = LocalDate.now()
    val remindersFlow = getAllAsFlowRemindersUseCase.invoke()
    private val intent = Intent(context, NotificationReminderReceiver::class.java)
    val isUpdateInProcessFlow = downloader.isUpdateInProcessFlow
    val percentageFlow = downloader.percentageFlow
    private val spListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            viewModelScope.launch(Dispatchers.IO) {
                _settings.emit(sharedPreferences.getSettingsOrCreateIfNull())
            }
        }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(spListener)
    }


    override fun removeEvent(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            eventCreator.removeEvent(reminder = reminder, intent = intent)
        }
    }

    override fun updateEvent(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            eventCreator.planeEvent(reminder = reminder, intent = intent)
        }
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(spListener)
    }
}