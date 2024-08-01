package ru.vafeen.reminder.noui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

}