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
import ru.vafeen.whisperoftasks.data.local_database.DatabaseRepository
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder
import ru.vafeen.whisperoftasks.data.network.downloader.Downloader
import ru.vafeen.whisperoftasks.domain.noui.EventCreation
import ru.vafeen.whisperoftasks.domain.noui.EventCreator
import ru.vafeen.whisperoftasks.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.whisperoftasks.presentation.MainActivity
import java.time.LocalDate


class MainScreenViewModel(
    private val databaseRepository: DatabaseRepository,
    private val sharedPreferences: SharedPreferences,
    override val eventCreator: EventCreator,
    private val downloader: Downloader,
    context: Context,
) : ViewModel(), EventCreation {
    private val _settings =
        MutableStateFlow(sharedPreferences.getSettingsOrCreateIfNull())
    val settings = _settings.asStateFlow()
    val todayDate: LocalDate = LocalDate.now()
    val pageNumber = 365
    val remindersFlow = databaseRepository.getAllRemindersAsFlow()
    private val intent = Intent(context, MainActivity::class.java)
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

    val countOfDaysInPast = 30
    val startDateInPast: LocalDate = todayDate.minusDays(countOfDaysInPast.toLong())
    private val _dateList = mutableListOf<LocalDate>()
    val dateList: List<LocalDate> = _dateList

    init {
        (0..pageNumber).forEach {
            _dateList.add(startDateInPast.plusDays(it.toLong()))
        }
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