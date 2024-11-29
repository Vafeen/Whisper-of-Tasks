package ru.vafeen.whisperoftasks.presentation.common.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import ru.vafeen.whisperoftasks.data.utils.Link
import ru.vafeen.whisperoftasks.data.network.repository.NetworkRepository
import ru.vafeen.whisperoftasks.presentation.utils.copyTextToClipBoard
import kotlin.system.exitProcess


internal class MainActivityViewModel(
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