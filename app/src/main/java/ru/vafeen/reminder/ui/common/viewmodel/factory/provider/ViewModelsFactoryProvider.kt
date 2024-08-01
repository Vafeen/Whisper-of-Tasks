package ru.vafeen.reminder.ui.common.viewmodel.factory.provider

import ru.vafeen.reminder.ui.common.viewmodel.factory.MainScreenViewModelFactory
import ru.vafeen.reminder.ui.common.viewmodel.factory.ReminderScreenViewModelFactory
import javax.inject.Inject

class ViewModelsFactoryProvider @Inject constructor(
    val mainScreenViewModelFactory: MainScreenViewModelFactory,
    val reminderScreenViewModelFactory: ReminderScreenViewModelFactory
)