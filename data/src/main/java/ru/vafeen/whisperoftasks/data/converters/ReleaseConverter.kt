package ru.vafeen.whisperoftasks.data.converters

import ru.vafeen.whisperoftasks.data.network.parcelable.github_service.ReleaseDTO
import ru.vafeen.whisperoftasks.domain.converter.BaseConverter
import ru.vafeen.whisperoftasks.domain.models.Release


internal class ReleaseConverter : BaseConverter<ReleaseDTO, Release> {

    // Конвертация из DTO в модель
    override fun convertAB(a: ReleaseDTO): Release {
        return Release(
            tagName = a.tagName,
            assets = a.assets.map { it.name },  // Извлекаем name из списка AssetDTO
            body = a.body
        )
    }

    // Конвертация из модели в DTO
    override fun convertBA(b: Release): ReleaseDTO =
        throw Exception("Converter from Release to ru.vafeen.whisperoftasks.data.network.parcelable.github_service.ReleaseDTO not found")
}