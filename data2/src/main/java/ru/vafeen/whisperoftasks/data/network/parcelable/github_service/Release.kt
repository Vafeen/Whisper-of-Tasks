package ru.vafeen.whisperoftasks.network.parcelable.github_service

import ru.vafeen.whisperoftasks.data.network.parcelable.github_service.Asset
import ru.vafeen.whisperoftasks.data.network.parcelable.github_service.Author

data class Release(
    val url: String,
    val assets_url: String,
    val upload_url: String,
    val html_url: String,
    val id: Long,
    val author: Author,
    val node_id: String,
    val tag_name: String,
    val target_commitish: String,
    val name: String,
    val draft: Boolean,
    val prerelease: Boolean,
    val created_at: String,
    val published_at: String,
    val assets: List<Asset>,
    val tarball_url: String,
    val zipball_url: String,
    val body: String,
)

