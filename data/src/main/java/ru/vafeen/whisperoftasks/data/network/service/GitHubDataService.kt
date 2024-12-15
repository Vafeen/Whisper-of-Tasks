package ru.vafeen.whisperoftasks.data.network.service

import retrofit2.Response
import retrofit2.http.GET
import ru.vafeen.whisperoftasks.data.network.end_points.GHDServiceLink
import ru.vafeen.whisperoftasks.data.network.parcelable.github_service.ReleaseDTO

internal interface GitHubDataService {
    @GET(GHDServiceLink.EndPoint.LATEST_RELEASE_INFO)
    suspend fun getLatestRelease(): Response<ReleaseDTO>
}