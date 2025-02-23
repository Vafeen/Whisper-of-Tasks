package ru.vafeen.whisperoftasks.presentation.components.trash_bin_screen

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import ru.vafeen.whisperoftasks.domain.database.usecase.DeleteAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.InsertAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager
import ru.vafeen.whisperoftasks.domain.utils.launchIO
import ru.vafeen.whisperoftasks.presentation.common.SelectingRemindersManager

class TrashBinScreenViewModel(
    getAllAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase,
    private val deleteAllRemindersUseCase: DeleteAllRemindersUseCase,
    private val insertAllRemindersUseCase: InsertAllRemindersUseCase,
    settingsManager: SettingsManager,
) : ViewModel(), SelectingRemindersManager {
    val settings = settingsManager.settingsFlow
    val remindersFlow = getAllAsFlowRemindersUseCase.invoke().map {
        it.filter { r -> r.isDeleted }
    }
    override val selectedReminders = mutableStateMapOf<Int, Reminder>()
    fun deleteForeverSelectedReminder() {
        viewModelScope.launchIO {
            selectedReminders.values.forEach { deleteAllRemindersUseCase.invoke(it) }
            clearSelectedReminders()
        }
    }

    fun restoreSelectedReminders() {
        viewModelScope.launchIO {
            insertAllRemindersUseCase.invoke(selectedReminders.values.map { it.copy(isDeleted = false) })
            clearSelectedReminders()
        }
    }
}
