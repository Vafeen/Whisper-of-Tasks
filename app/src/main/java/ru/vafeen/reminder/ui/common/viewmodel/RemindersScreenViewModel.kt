package ru.vafeen.reminder.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import ru.vafeen.reminder.noui.EventCreator
import ru.vafeen.reminder.noui.local_database.DatabaseRepository


class RemindersScreenViewModel(
    val databaseRepository: DatabaseRepository,
    val eventCreator: EventCreator,
) : ViewModel()