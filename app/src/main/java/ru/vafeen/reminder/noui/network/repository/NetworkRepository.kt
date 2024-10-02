package ru.vafeen.reminder.network.repository

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Url
import ru.vafeen.reminder.network.parcelable.github_service.Release
import ru.vafeen.reminder.network.service.DownloadService
import ru.vafeen.reminder.network.service.GitHubDataService

class NetworkRepository(
    private val gitHubDataService: GitHubDataService,
    private val downloadService: DownloadService,
) {

    suspend fun getLatestRelease(): Response<Release>? = try {
        gitHubDataService.getLatestRelease()
    } catch (e: Exception) {
        null
    }

    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>? = try {
        downloadService.downloadFile(fileUrl = fileUrl)
    } catch (e: Exception) {
        null
    }
}