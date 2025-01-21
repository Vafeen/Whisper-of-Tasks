package ru.vafeen.whisperoftasks.domain.network

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.network.usecase.GetLatestReleaseUseCase

internal val NetworkModule = module {
    singleOf(::GetLatestReleaseUseCase)
}