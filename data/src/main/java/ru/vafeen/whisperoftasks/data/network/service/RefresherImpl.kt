package ru.vafeen.whisperoftasks.data.network.service

import android.content.Context
import android.vafeen.direct_refresher.DirectRefresher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import ru.vafeen.whisperoftasks.data.converters.toDownloadStatus
import ru.vafeen.whisperoftasks.data.network.end_points.DownloadServiceLink
import ru.vafeen.whisperoftasks.domain.network.service.Refresher
import android.vafeen.direct_refresher.refresher.Refresher as LibRefresher

internal class RefresherImpl(context: Context) : Refresher {
    private val libRefresher: LibRefresher =
        DirectRefresher.provideRefresher(
            context = context,
            downloader = DirectRefresher.provideDownloader(
                context = context,
                baseUrl = DownloadServiceLink.BASE_LINK
            ),
            installer = DirectRefresher.provideInstaller(context = context)
        )
    override val progressFlow =
        libRefresher.progressFlow.map { it.toDownloadStatus() }

    override suspend fun refresh(
        coroutineScope: CoroutineScope,
        url: String,
        downloadedFileName: String
    ) = libRefresher.refresh(coroutineScope, url, downloadedFileName)

}