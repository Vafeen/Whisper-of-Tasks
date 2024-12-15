package ru.vafeen.whisperoftasks.main

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.vafeen.whisperoftasks.data.di.base.baseDataModule
import ru.vafeen.whisperoftasks.domain.di.base.baseDomainModule
import ru.vafeen.whisperoftasks.presentation.di.base.basePresentationModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                baseDomainModule,
                baseDataModule,
                basePresentationModule
            )
        }

    }
}