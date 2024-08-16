package ru.vafeen.reminder.noui.viewmodel

import androidx.lifecycle.ViewModel
import ru.vafeen.reminder.noui.viewmodel.factory.MainScreenViewModelFactory
import ru.vafeen.reminder.noui.viewmodel.factory.ReminderScreenViewModelFactory
import ru.vafeen.reminder.noui.viewmodel.factory.SettingsScreenViewModelFactory
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    val mainScreenViewModelFactory: MainScreenViewModelFactory,
    val reminderScreenViewModelFactory: ReminderScreenViewModelFactory,
    val settingsScreenViewModelFactory: SettingsScreenViewModelFactory,
) : ViewModel() {

}