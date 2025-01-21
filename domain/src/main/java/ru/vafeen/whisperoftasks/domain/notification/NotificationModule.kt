package ru.vafeen.whisperoftasks.domain.notification

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.notification.usecase.NotificationReminderRecoveryUseCase
import ru.vafeen.whisperoftasks.domain.notification.usecase.ShowNotificationTaskUseCase

internal val NotificationModule = module {
    singleOf(::NotificationReminderRecoveryUseCase)
    singleOf(::ShowNotificationTaskUseCase)
}