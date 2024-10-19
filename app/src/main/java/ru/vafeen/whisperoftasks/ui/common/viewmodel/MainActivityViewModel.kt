package ru.vafeen.whisperoftasks.ui.common.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import ru.vafeen.whisperoftasks.network.repository.NetworkRepository
import ru.vafeen.whisperoftasks.utils.Link
import ru.vafeen.whisperoftasks.utils.copyTextToClipBoard
import kotlin.system.exitProcess


class MainActivityViewModel(
    val networkRepository: NetworkRepository,
) : ViewModel() {
    var updateIsShowed: Boolean = false
    fun registerGeneralExceptionCallback(context: Context) {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            context.copyTextToClipBoard(
                label = "Error",
                text = "Contact us about this problem: ${Link.MAIL}\n\n Exception in ${thread.name} thread\n${throwable.stackTraceToString()}"
            )
            Log.e("GeneralException", "Exception in thread ${thread.name}", throwable)
            exitProcess(0)
        }
    }
}