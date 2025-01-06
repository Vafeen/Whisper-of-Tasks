package ru.vafeen.whisperoftasks.presentation.components.reminders_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.vafeen.whisperoftasks.domain.database.ReminderRepository
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

internal class RemindersScreenViewModel(private val reminderRepository: ReminderRepository) :
    ViewModel() {
    val remindersFlow =
        reminderRepository.getAllRemindersAsFlow().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )
    private val remindersForDeleting = mutableMapOf<Int, Reminder>()
    private val _remindersForDeletingFlow = MutableStateFlow(listOf<Int>())
    val remindersForDeletingFlow = _remindersForDeletingFlow.asStateFlow()
    private fun updateRemindersForDeletingFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            _remindersForDeletingFlow.emit(remindersForDeleting.keys.toList())
        }
    }

    fun setReminderAsCandidateForDeleting(reminder: Reminder) {
        remindersForDeleting[reminder.idOfReminder] = reminder
        updateRemindersForDeletingFlow()
    }

    private fun unsetReminderAsCandidateForDeleting(reminder: Reminder) {
        remindersForDeleting.remove(reminder.idOfReminder)
        updateRemindersForDeletingFlow()
    }

    private fun isThisReminderIsCandidateForDeleting(reminder: Reminder): Boolean =
        remindersForDeleting.containsKey(reminder.idOfReminder)

    fun clearRemindersForDeleting() {
        remindersForDeleting.clear()
        updateRemindersForDeletingFlow()
    }

    fun changeStatusForDeleting(reminder: Reminder) =
        if (!isThisReminderIsCandidateForDeleting(reminder))
            setReminderAsCandidateForDeleting(reminder)
        else unsetReminderAsCandidateForDeleting(reminder)


    fun removeEventsForReminderForDeleting() {
        remindersForDeleting.values.forEach {
            // TODO убрать ивент
            //TODO удалить из бд
        }
    }
}