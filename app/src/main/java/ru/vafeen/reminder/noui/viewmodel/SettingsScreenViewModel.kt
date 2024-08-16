package ru.vafeen.reminder.noui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel()