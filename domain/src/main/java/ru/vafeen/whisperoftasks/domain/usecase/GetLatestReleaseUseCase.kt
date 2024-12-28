package ru.vafeen.whisperoftasks.domain.usecase

import ru.vafeen.whisperoftasks.domain.models.Release
import ru.vafeen.whisperoftasks.domain.network.ReleaseRepository
import ru.vafeen.whisperoftasks.domain.network.result.ResponseResult

class GetLatestReleaseUseCase(private val releaseRepository: ReleaseRepository) {
    suspend operator fun invoke(): ResponseResult<Release> = releaseRepository.getLatestRelease()
}