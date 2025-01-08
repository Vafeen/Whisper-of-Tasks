package ru.vafeen.whisperoftasks.tests.mock

import org.koin.dsl.module
import ru.vafeen.whisperoftasks.tests.mock.database.DatabaseModule

internal val MainMockModule = module {
    includes(DatabaseModule)
}