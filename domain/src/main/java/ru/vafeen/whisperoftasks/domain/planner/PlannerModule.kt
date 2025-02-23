package ru.vafeen.whisperoftasks.domain.planner

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.planner.usecase.MakeRecoveryNeededUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.ReminderRecoveryUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.PlanWorkUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.CancelWorkUseCase

internal val PlannerModule = module {
    singleOf(::MakeRecoveryNeededUseCase)
    singleOf(::ReminderRecoveryUseCase)
    singleOf(::CancelWorkUseCase)
    singleOf(::PlanWorkUseCase)
}