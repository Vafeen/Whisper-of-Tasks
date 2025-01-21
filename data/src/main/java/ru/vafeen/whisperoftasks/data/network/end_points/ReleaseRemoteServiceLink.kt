package ru.vafeen.whisperoftasks.data.network.end_points

internal object ReleaseRemoteServiceLink {
    const val BASE_LINK = "https://api.github.com/"

    object EndPoint {
        const val LATEST_RELEASE_INFO = "repos/vafeen/UniversitySchedule/releases/latest"
    }
}