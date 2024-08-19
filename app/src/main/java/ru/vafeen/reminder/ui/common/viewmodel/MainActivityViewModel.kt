package ru.vafeen.reminder.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.vafeen.reminder.ui.common.viewmodel.factory.MainScreenViewModelFactory
import ru.vafeen.reminder.ui.common.viewmodel.factory.ReminderScreenViewModelFactory
import ru.vafeen.reminder.ui.common.viewmodel.factory.SettingsScreenViewModelFactory
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val mainScreenViewModelFactory: MainScreenViewModelFactory,
    val reminderScreenViewModelFactory: ReminderScreenViewModelFactory,
    val settingsScreenViewModelFactory: SettingsScreenViewModelFactory,
) : ViewModel() {

}