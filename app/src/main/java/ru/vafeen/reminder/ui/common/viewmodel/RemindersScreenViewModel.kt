package ru.vafeen.reminder.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vafeen.reminder.noui.EventCreator
import ru.vafeen.reminder.noui.local_database.DatabaseRepository
import ru.vafeen.reminder.noui.local_database.entity.Reminder


class RemindersScreenViewModel(
    val databaseRepository: DatabaseRepository,
    override val eventCreator: EventCreator,
) : ViewModel(), EventCreation {

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