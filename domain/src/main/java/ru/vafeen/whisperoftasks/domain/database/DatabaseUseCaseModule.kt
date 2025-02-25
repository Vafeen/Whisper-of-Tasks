package ru.vafeen.whisperoftasks.domain.database

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.database.usecase.DeleteAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.GetReminderByIdOfReminderUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.InsertAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.MoveFromTrashBinReminderUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.MoveToTrashBinReminderUseCase

internal val DatabaseUseCaseModule = module {
    singleOf(::DeleteAllRemindersUseCase)
    singleOf(::GetAllAsFlowRemindersUseCase)
    singleOf(::GetReminderByIdOfReminderUseCase)
    singleOf(::InsertAllRemindersUseCase)
    singleOf(::MoveFromTrashBinReminderUseCase)
    singleOf(::MoveToTrashBinReminderUseCase)
}