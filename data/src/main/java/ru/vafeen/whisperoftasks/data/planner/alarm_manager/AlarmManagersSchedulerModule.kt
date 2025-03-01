package ru.vafeen.whisperoftasks.data.planner.alarm_manager

import android.app.AlarmManager
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.planner.Scheduler

internal val AlarmManagersSchedulerModule = module {
    singleOf(::AlarmManagerSchedulerImpl)
    single<Scheduler>(qualifier = named(Scheduler.Companion.Choice.ALARM_MANAGER.value)) {
        get<AlarmManagerSchedulerImpl>()
    }
    single<AlarmManager> {
        androidContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
}