package ru.vafeen.reminder.noui.viewmodel.factory.provider

import ru.vafeen.reminder.noui.viewmodel.factory.MainScreenViewModelFactory
import ru.vafeen.reminder.noui.viewmodel.factory.ReminderScreenViewModelFactory
import javax.inject.Inject

class ViewModelsFactoryProvider @Inject constructor(
    val mainScreenViewModelFactory: MainScreenViewModelFactory,
    val reminderScreenViewModelFactory: ReminderScreenViewModelFactory
)