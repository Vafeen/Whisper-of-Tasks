package ru.vafeen.whisperoftasks.domain.noui.network.usecase

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Url
import ru.vafeen.whisperoftasks.data.network.service.DownloadService

internal class DownloadFileUseCase(private val downloadService: DownloadService) {
    operator fun invoke(@Url fileUrl: String): Call<ResponseBody>? = try {
        downloadService.downloadFile(fileUrl = fileUrl)
    } catch (e: Exception) {
        null
    }
}