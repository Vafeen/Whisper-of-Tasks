package ru.vafeen.whisperoftasks.data.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager

internal val SharedPreferencesModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
        )
    }
    singleOf(::SettingsManagerImpl) {
        bind<SettingsManager>()
    }
}