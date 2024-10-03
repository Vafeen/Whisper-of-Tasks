package ru.vafeen.reminder.ui.common.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vafeen.reminder.R
import ru.vafeen.reminder.noui.EventCreator
import ru.vafeen.reminder.noui.local_database.DatabaseRepository
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import ru.vafeen.reminder.noui.shared_preferences.SharedPreferences
import ru.vafeen.reminder.utils.getSettingsOrCreateIfNull
import java.time.LocalDate


class MainScreenViewModel(
    val databaseRepository: DatabaseRepository,
    private val sharedPreferences: SharedPreferences,
    override val eventCreator: EventCreator,
    context: Context
) : ViewModel(), EventCreatorViewModel {
    val ruDaysOfWeek =
        context.let {
            listOf(
                it.getString(R.string.monday),
                it.getString(R.string.tuesday),
                it.getString(R.string.wednesday),
                it.getString(R.string.thursday),
                it.getString(R.string.friday),
                it.getString(R.string.satudray),
                it.getString(R.string.sunday)
            )
        }
    var settings = sharedPreferences.getSettingsOrCreateIfNull()
    val todayDate: LocalDate = LocalDate.now()
    val pageNumber = 365

    override fun removeEvent(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            eventCreator.removeEvent(reminder = reminder)
        }
    }
}