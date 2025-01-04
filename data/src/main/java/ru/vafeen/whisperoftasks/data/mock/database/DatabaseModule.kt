package ru.vafeen.whisperoftasks.data.mock.database

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.database.ReminderRepository

internal val DatabaseModule = module {
    singleOf(::MockReminderRepositoryImpl) {
        bind<ReminderRepository>()
    }
}