package ru.vafeen.whisperoftasks.domain.planner

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.planner.usecase.MakeRecoveryNeededUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.ReminderRecoveryUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.PlanWorkUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.CancelWorkUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.ChooseSchedulerUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.GetSchedulerDependsOnSettings

internal val PlannerModule = module {
    singleOf(::CancelWorkUseCase)
    singleOf(::ChooseSchedulerUseCase)
    singleOf(::GetSchedulerDependsOnSettings)
    singleOf(::MakeRecoveryNeededUseCase)
    singleOf(::PlanWorkUseCase)
    singleOf(::ReminderRecoveryUseCase)
}