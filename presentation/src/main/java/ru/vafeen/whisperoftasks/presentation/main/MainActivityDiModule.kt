package ru.vafeen.whisperoftasks.presentation.main

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.MainActivityIntentProvider

internal val MainActivityDiModule = module {
    viewModelOf(::MainActivityViewModel)
    singleOf(::MainActivityIntentProviderImpl) {
        bind<MainActivityIntentProvider>()
    }
}