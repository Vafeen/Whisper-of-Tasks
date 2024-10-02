package ru.vafeen.reminder.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import ru.vafeen.reminder.network.repository.NetworkRepository


class MainActivityViewModel(
    val networkRepository: NetworkRepository
) : ViewModel() {
    var updateIsShowed: Boolean = false
}