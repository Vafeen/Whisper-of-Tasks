package ru.vafeen.whisperoftasks.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import ru.vafeen.whisperoftasks.network.repository.NetworkRepository


class MainActivityViewModel(
    val networkRepository: NetworkRepository,
) : ViewModel() {
    var updateIsShowed: Boolean = false
}