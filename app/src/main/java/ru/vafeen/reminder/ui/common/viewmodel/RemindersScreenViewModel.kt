package ru.vafeen.reminder.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.vafeen.reminder.noui.EventCreator
import ru.vafeen.reminder.noui.local_database.DatabaseRepository
import javax.inject.Inject

@HiltViewModel
class RemindersScreenViewModel @Inject constructor(
    val databaseRepository: DatabaseRepository,
    val eventCreator: EventCreator,
) : ViewModel()