package ru.vafeen.whisperoftasks.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vafeen.whisperoftasks.noui.EventCreator
import ru.vafeen.whisperoftasks.noui.local_database.DatabaseRepository
import ru.vafeen.whisperoftasks.noui.local_database.entity.Reminder
import ru.vafeen.whisperoftasks.ui.common.components.EventCreation


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