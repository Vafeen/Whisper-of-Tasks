package ru.vafeen.reminder.ui.common.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

//@HiltViewModel
class RemindersScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

}