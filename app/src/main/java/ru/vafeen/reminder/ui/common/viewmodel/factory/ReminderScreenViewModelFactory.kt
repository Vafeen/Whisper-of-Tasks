package ru.vafeen.reminder.ui.common.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.vafeen.reminder.noui.local_database.DatabaseRepository
import ru.vafeen.reminder.ui.common.viewmodel.RemindersScreenViewModel
import javax.inject.Inject

class ReminderScreenViewModelFactory @Inject constructor(
    @ApplicationContext private val context: Context,
    private val databaseRepository: DatabaseRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemindersScreenViewModel::class.java)) {
            return RemindersScreenViewModel(
                context = context,
                databaseRepository = databaseRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}