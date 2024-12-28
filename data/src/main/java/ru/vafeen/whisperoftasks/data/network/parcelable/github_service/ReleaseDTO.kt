package ru.vafeen.whisperoftasks.data.network.parcelable.github_service

import com.google.gson.annotations.SerializedName

internal data class ReleaseDTO(
    val url: String,
    @SerializedName("assets_url") val assetsUrl: String,
    @SerializedName("upload_url") val uploadUrl: String,
    @SerializedName("html_url") val htmlUrl: String,
    val id: Long,
    val author: AuthorDTO,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("tag_name") val tagName: String,
    @SerializedName("target_commitish") val targetCommitish: String,
    val name: String,
    val draft: Boolean,
    val prerelease: Boolean,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("published_at") val publishedAt: String,
    val assets: List<AssetDTO>,
    @SerializedName("tarball_url") val tarballUrl: String,
    @SerializedName("zipball_url") val zipballUrl: String,
    val body: String,
)