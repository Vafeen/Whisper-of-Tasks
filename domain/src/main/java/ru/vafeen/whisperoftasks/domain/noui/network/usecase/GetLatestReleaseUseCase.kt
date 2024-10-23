package ru.vafeen.whisperoftasks.domain.noui.network.usecase

import retrofit2.Response
import ru.vafeen.whisperoftasks.data.network.service.GitHubDataService
import ru.vafeen.whisperoftasks.network.parcelable.github_service.Release

internal class GetLatestReleaseUseCase(private val gitHubDataService: GitHubDataService) {
    suspend operator fun invoke(): Response<Release>? = try {
        gitHubDataService.getLatestRelease()
    } catch (e: Exception) {
        null
    }
}