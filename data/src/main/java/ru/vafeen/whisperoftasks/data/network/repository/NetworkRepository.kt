package ru.vafeen.whisperoftasks.data.network.repository

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Url
import ru.vafeen.whisperoftasks.network.parcelable.github_service.Release

interface NetworkRepository {
    suspend fun getLatestRelease(): Response<Release>?
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>?
}