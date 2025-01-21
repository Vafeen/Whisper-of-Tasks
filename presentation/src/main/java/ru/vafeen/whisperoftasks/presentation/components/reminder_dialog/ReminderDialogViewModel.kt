package ru.vafeen.whisperoftasks.presentation.components.reminder_dialog

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.vafeen.whisperoftasks.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.InsertAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.planner.usecase.RemoveEventUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.SetEventUseCase
import ru.vafeen.whisperoftasks.domain.utils.generateID
import ru.vafeen.whisperoftasks.domain.utils.getDateStringWithWeekOfDay
import ru.vafeen.whisperoftasks.domain.utils.getTimeDefaultStr
import ru.vafeen.whisperoftasks.presentation.common.EventCreation
import ru.vafeen.whisperoftasks.resources.R
import java.time.LocalDateTime

internal class ReminderDialogViewModel(
    private val removeEventUseCase: RemoveEventUseCase,
    private val setEventUseCase: SetEventUseCase,
    private val insertAllRemindersUseCase: InsertAllRemindersUseCase,
    private val getAllAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase,
) : ViewModel(), EventCreation {

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            insertAllRemindersUseCase.invoke(reminder)
        }
    }

    override fun removeEvent(reminder: Reminder) {
        viewModelScope.launch {
            removeEventUseCase(reminder)
        }
    }

    override fun updateEvent(reminder: Reminder) {
        viewModelScope.launch {
            setEventUseCase(
                reminder.copy(
                    idOfReminder = getAllAsFlowRemindersUseCase.invoke().first()
                        .map { it.idOfReminder }.generateID(),
                )
            )
        }
    }

    fun mainButtonText(context: Context, selectedDateTime: LocalDateTime, reminder: Reminder) =
        if (reminder.isNotificationNeeded) "${
            context.getString(R.string.send)
        } ${selectedDateTime.getDateStringWithWeekOfDay(context = context)} ${
            context.getString(R.string.`in`)
        } ${selectedDateTime.hour.getTimeDefaultStr()}:${selectedDateTime.minute.getTimeDefaultStr()}"
        else context.getString(R.string.add_to_list)

}