package ru.vafeen.whisperoftasks.domain.database.usecase

import kotlinx.coroutines.flow.Flow
import ru.vafeen.whisperoftasks.domain.database.repository.ReminderLocalRepository
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

 class GetAllAsFlowRemindersUseCase(private val reminderLocalRepository: ReminderLocalRepository) {
    operator fun invoke(): Flow<List<Reminder>> = reminderLocalRepository.getAllRemindersAsFlow()
}