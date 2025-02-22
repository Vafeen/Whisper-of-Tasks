package ru.vafeen.whisperoftasks.domain.planner

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.planner.usecase.MakeRecoveryNeededUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.ReminderRecoveryUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.SetEventUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.UnsetEventUseCase

internal val PlannerModule = module {
    singleOf(::MakeRecoveryNeededUseCase)
    singleOf(::ReminderRecoveryUseCase)
    singleOf(::UnsetEventUseCase)
    singleOf(::SetEventUseCase)
}