package ru.vafeen.reminder.noui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.vafeen.reminder.noui.viewmodel.MainScreenViewModel
import ru.vafeen.reminder.noui.viewmodel.RemindersScreenViewModel
import javax.inject.Inject

class ReminderScreenViewModelFactory @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemindersScreenViewModel::class.java)) {
            return RemindersScreenViewModel(context = context) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}