package ru.vafeen.whisperoftasks.domain.di

import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.planner.PlannerModule

val MainDomainModule = module {
    includes(PlannerModule)
}