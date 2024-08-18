package ru.vafeen.reminder.ui.common.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.vafeen.reminder.ui.common.viewmodel.MainScreenViewModel
import javax.inject.Inject

class MainScreenViewModelFactory @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            return MainScreenViewModel(context = context) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}