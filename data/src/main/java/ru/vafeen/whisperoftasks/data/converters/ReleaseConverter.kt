package ru.vafeen.whisperoftasks.data.converters

import ru.vafeen.universityschedule.data.network.dto.github_service.ReleaseDTO
import ru.vafeen.whisperoftasks.domain.domain_models.Release

/**
 * Конвертер для преобразования [ReleaseDTO] в [Release].
 *
 * Данный конвертер предназначен для преобразования данных о релизах,
 * полученных с сервера, в доменную модель, используемую в приложении.
 */
internal fun ReleaseDTO.toRelease(): Release? {
    val apk = this.assets.find {
        it.browserDownloadUrl.contains(".apk")
    }
    return if (apk != null) {
        Release(
            tagName = this.tagName,
            apkUrl = apk.browserDownloadUrl,
            size = apk.size,
            body = this.body
        )
    } else null
}
