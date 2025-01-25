package ru.vafeen.whisperoftasks.data.converters

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val ConvertersModule = module {
    singleOf(::DateConverter)
    singleOf(::DateTimeConverter)
    singleOf(::DownloadStatusConverter)
    singleOf(::DurationConverter)
    singleOf(::ReleaseConverter)
    singleOf(::ReminderConverter)
}