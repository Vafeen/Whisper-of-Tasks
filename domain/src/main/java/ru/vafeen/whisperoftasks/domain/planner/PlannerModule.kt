package ru.vafeen.whisperoftasks.domain.planner

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.planner.usecase.RemoveEventUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.SetEventUseCase

internal val PlannerModule = module {
    singleOf(::RemoveEventUseCase)
    singleOf(::SetEventUseCase)
}