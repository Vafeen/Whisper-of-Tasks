package ru.vafeen.whisperoftasks.domain.di.base

import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.di.servicesModule
import ru.vafeen.whisperoftasks.domain.di.useCaseModule

val baseDomainModule = module {
    includes(useCaseModule, servicesModule)
}