package ru.vafeen.whisperoftasks.data.network.parcelable.github_service

import com.google.gson.annotations.SerializedName

internal data class AssetDTO(
    val url: String,
    val id: Long,
    @SerializedName("node_id") val nodeId: String,
    val name: String,
    val label: String?,
    val uploader: AuthorDTO,
    @SerializedName("content_type") val contentType: String,
    val state: String,
    val size: Long,
    @SerializedName("download_count") val downloadCount: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("browser_download_url") val browserDownloadUrl: String,
)