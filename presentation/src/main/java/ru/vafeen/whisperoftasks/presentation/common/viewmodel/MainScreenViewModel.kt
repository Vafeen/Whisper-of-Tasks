package ru.vafeen.whisperoftasks.presentation.common.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vafeen.whisperoftasks.data.local_database.DatabaseRepository
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder
import ru.vafeen.whisperoftasks.data.shared_preferences.SharedPreferences
import ru.vafeen.whisperoftasks.data.utils.getSettingsOrCreateIfNull
import ru.vafeen.whisperoftasks.domain.noui.EventCreation
import ru.vafeen.whisperoftasks.domain.noui.EventCreator
import ru.vafeen.whisperoftasks.presentation.MainActivity
import java.time.LocalDate


class MainScreenViewModel(
    private val databaseRepository: DatabaseRepository,
    private val sharedPreferences: SharedPreferences,
    override val eventCreator: EventCreator,
    context: Context,
) : ViewModel(), EventCreation {
    var settings = sharedPreferences.getSettingsOrCreateIfNull()
    val todayDate: LocalDate = LocalDate.now()
    val pageNumber = 365
    val remindersFlow = databaseRepository.getAllRemindersAsFlow()
    val intent = Intent(context, MainActivity::class.java)
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
}