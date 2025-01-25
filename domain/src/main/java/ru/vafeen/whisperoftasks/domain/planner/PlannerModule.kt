package ru.vafeen.whisperoftasks.domain.planner

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.planner.usecase.SetEventUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.UnsetEventUseCase

internal val PlannerModule = module {
    singleOf(::UnsetEventUseCase)
    singleOf(::SetEventUseCase)
}