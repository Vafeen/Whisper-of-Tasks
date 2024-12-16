package ru.vafeen.whisperoftasks.data.network.impl

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.vafeen.whisperoftasks.data.utils.pathToDownloadRelease
import ru.vafeen.whisperoftasks.domain.network.DownloadFileRepository
import ru.vafeen.whisperoftasks.domain.network.Downloader
import ru.vafeen.whisperoftasks.domain.network.Installer
import ru.vafeen.whisperoftasks.domain.network.result.ResponseResult
import java.io.File
import java.io.FileOutputStream

internal class DownloaderImpl(
    private val context: Context,
    private val downloadFileRepository: DownloadFileRepository,
    private val installer: Installer
) : Downloader {
    private val _percentageFlow = MutableSharedFlow<Float>()
    override val percentageFlow = _percentageFlow.asSharedFlow()

    private val _isUpdateInProcessFlow = MutableSharedFlow<Boolean>()
    override val isUpdateInProcessFlow = _isUpdateInProcessFlow.asSharedFlow()

    override fun downloadApk(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _isUpdateInProcessFlow.emit(true)
            val apkFilePath = context.pathToDownloadRelease()
            when (val result = downloadFileRepository.downloadFile(url)) {
                is ResponseResult.Error -> {
                    Log.e("error", result.exception.message.toString())
                    _isUpdateInProcessFlow.emit(false)
                    _percentageFlow.emit(0f)
                }

                is ResponseResult.Success -> {
                    val file = File(apkFilePath)
                    val inputStream = result.data.stream
                    val outputStream = FileOutputStream(file)
                    val buffer = ByteArray(8 * 1024)
                    var bytesRead: Int
                    var totalBytesRead: Long = 0
                    val contentLength = result.data.contentLength

                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                        totalBytesRead += bytesRead
                        _percentageFlow.emit(totalBytesRead.toFloat() / contentLength)
                    }

                    outputStream.close()
                    inputStream.close()
                    _isUpdateInProcessFlow.emit(false)
                    installer.installApk()
                }
            }
        }
    }
}