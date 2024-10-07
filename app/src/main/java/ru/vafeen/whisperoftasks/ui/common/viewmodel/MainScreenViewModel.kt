package ru.vafeen.whisperoftasks.ui.common.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vafeen.whisperoftasks.noui.EventCreator
import ru.vafeen.whisperoftasks.noui.local_database.DatabaseRepository
import ru.vafeen.whisperoftasks.noui.local_database.entity.Reminder
import ru.vafeen.whisperoftasks.noui.shared_preferences.SharedPreferences
import ru.vafeen.whisperoftasks.ui.common.components.EventCreation
import ru.vafeen.whisperoftasks.utils.getSettingsOrCreateIfNull
import java.time.LocalDate


class MainScreenViewModel(
    val databaseRepository: DatabaseRepository,
    private val sharedPreferences: SharedPreferences,
    override val eventCreator: EventCreator,
    context: Context,
) : ViewModel(), EventCreation {
    var settings = sharedPreferences.getSettingsOrCreateIfNull()
    val todayDate: LocalDate = LocalDate.now()
    val pageNumber = 365

    override fun removeEvent(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            eventCreator.removeEvent(reminder = reminder)
        }
    }

    override fun updateEvent(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            eventCreator.planeEvent(reminder = reminder)
        }
    }
}