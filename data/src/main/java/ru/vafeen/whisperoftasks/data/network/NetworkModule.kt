package ru.vafeen.whisperoftasks.data.network

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vafeen.whisperoftasks.data.network.end_points.ReleaseRemoteServiceLink
import ru.vafeen.whisperoftasks.data.network.repository.ReleaseRemoteRepositoryImpl
import ru.vafeen.whisperoftasks.data.network.service.RefresherImpl
import ru.vafeen.whisperoftasks.data.network.service.ReleaseRemoteService
import ru.vafeen.whisperoftasks.domain.network.repository.ReleaseRemoteRepository
import ru.vafeen.whisperoftasks.domain.network.service.Refresher

internal val NetworkModule = module {
    singleOf(::ReleaseRemoteRepositoryImpl) {
        bind<ReleaseRemoteRepository>()
    }
    single<ReleaseRemoteService> {
        Retrofit.Builder()
            .baseUrl(ReleaseRemoteServiceLink.BASE_LINK)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ReleaseRemoteService::class.java)
    }
    singleOf(::RefresherImpl) {
        bind<Refresher>()
    }
}