package ru.vafeen.whisperoftasks.domain.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.shared_preferences.SharedPreferencesValue
import ru.vafeen.whisperoftasks.domain.usecase.DeleteAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.usecase.GetLatestReleaseUseCase
import ru.vafeen.whisperoftasks.domain.usecase.GetReminderByIdOfReminderUseCase
import ru.vafeen.whisperoftasks.domain.usecase.InsertAllRemindersUseCase

internal val useCaseModule = module {
    singleOf(::DeleteAllRemindersUseCase)

    singleOf(::GetAllAsFlowRemindersUseCase)
    singleOf(::GetLatestReleaseUseCase)
    singleOf(::GetReminderByIdOfReminderUseCase)
    singleOf(::InsertAllRemindersUseCase)
}
internal val servicesModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
        )
    }

}