package ru.vafeen.reminder.ui.common.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.vafeen.reminder.ui.common.viewmodel.SettingsScreenViewModel
import javax.inject.Inject

class SettingsScreenViewModelFactory @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsScreenViewModel::class.java)) {
            return SettingsScreenViewModel(context = context) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}