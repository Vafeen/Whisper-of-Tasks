package ru.vafeen.whisperoftasks.data.planner.work_manager

import androidx.work.WorkManager
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.planner.Scheduler

internal val WorkManagerSchedulerImplModule = module {
    singleOf(::WorkManagerSchedulerImpl) { bind<Scheduler>() }
    single<WorkManager> {
        WorkManager.getInstance(get())
    }
}