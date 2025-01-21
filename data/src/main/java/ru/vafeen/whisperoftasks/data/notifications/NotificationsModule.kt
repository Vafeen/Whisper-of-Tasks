package ru.vafeen.whisperoftasks.data.notifications

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.notification.NotificationBuilder
import ru.vafeen.whisperoftasks.domain.notification.NotificationService

internal val NotificationsModule = module {
    singleOf(::NotificationBuilderImpl) {
        bind<NotificationBuilder>()
    }
    singleOf(::NotificationServiceImpl) {
        bind<NotificationService>()
    }
}