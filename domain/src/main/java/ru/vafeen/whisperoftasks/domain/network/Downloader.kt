package ru.vafeen.whisperoftasks.domain.network

import kotlinx.coroutines.flow.SharedFlow

interface Downloader {
    val percentageFlow: SharedFlow<Float>
    val isUpdateInProcessFlow: SharedFlow<Boolean>
    fun downloadApk(url: String)
    fun installApk()
}