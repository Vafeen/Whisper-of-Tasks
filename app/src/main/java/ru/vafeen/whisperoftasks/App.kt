package ru.vafeen.whisperoftasks

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.vafeen.whisperoftasks.data.di.MainDataModule
import ru.vafeen.whisperoftasks.domain.di.MainDomainModule
import ru.vafeen.whisperoftasks.presentation.common.di.MainPresentationModule

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(MainPresentationModule, MainDataModule, MainDomainModule)
        }
    }
}