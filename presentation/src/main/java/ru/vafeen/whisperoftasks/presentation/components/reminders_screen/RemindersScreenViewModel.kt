package ru.vafeen.whisperoftasks.presentation.components.reminders_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.vafeen.whisperoftasks.domain.database.ReminderRepository

internal class RemindersScreenViewModel(private val reminderRepository: ReminderRepository) :
    ViewModel() {
    val remindersFlow =
        reminderRepository.getAllRemindersAsFlow().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

}