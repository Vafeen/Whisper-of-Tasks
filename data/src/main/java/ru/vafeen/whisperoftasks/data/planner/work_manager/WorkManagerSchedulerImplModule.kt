package ru.vafeen.whisperoftasks.data.planner.work_manager

import androidx.work.WorkManager
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.planner.Scheduler

internal val WorkManagerSchedulerImplModule = module {
    singleOf(::WorkManagerSchedulerImpl)
    single<Scheduler>(qualifier = named(Scheduler.Companion.Choice.WORK_MANAGER.value)) {
        get<WorkManagerSchedulerImpl>()
    }
    single<WorkManager> {
        WorkManager.getInstance(get())
    }
}