package ru.vafeen.reminder.ui.common.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vafeen.reminder.noui.EventCreator
import ru.vafeen.reminder.noui.local_database.DatabaseRepository
import ru.vafeen.reminder.ui.common.viewmodel.RemindersScreenViewModel
import javax.inject.Inject

class ReminderScreenViewModelFactory @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val eventCreator: EventCreator
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemindersScreenViewModel::class.java)) {
            return RemindersScreenViewModel(
                databaseRepository = databaseRepository,
                eventCreator = eventCreator
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}