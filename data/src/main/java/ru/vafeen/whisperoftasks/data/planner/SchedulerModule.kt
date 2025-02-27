package ru.vafeen.whisperoftasks.data.planner

import androidx.work.WorkManager
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.planner.Scheduler

internal val SchedulerModule = module {
    singleOf(::SchedulerImpl) { bind<Scheduler>() }
    single<WorkManager> {
        WorkManager.getInstance(get())
    }
}