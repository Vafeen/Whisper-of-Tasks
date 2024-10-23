package ru.vafeen.whisperoftasks.domain.noui.network


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Url
import ru.vafeen.whisperoftasks.data.network.repository.NetworkRepository
import ru.vafeen.whisperoftasks.domain.noui.network.usecase.DownloadFileUseCase
import ru.vafeen.whisperoftasks.domain.noui.network.usecase.GetLatestReleaseUseCase
import ru.vafeen.whisperoftasks.network.parcelable.github_service.Release

internal class NetworkRepositoryImpl(
    private val getLatestReleaseUseCase: GetLatestReleaseUseCase,
    private val downloadServiceUseCase: DownloadFileUseCase,
) : NetworkRepository {

    override suspend fun getLatestRelease(): Response<Release>? = getLatestReleaseUseCase.invoke()

    override fun downloadFile(@Url fileUrl: String): Call<ResponseBody>? =
        downloadServiceUseCase(fileUrl = fileUrl)
}