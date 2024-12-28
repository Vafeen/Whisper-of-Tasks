package ru.vafeen.whisperoftasks.domain.models

data class Release(
    val tagName: String,
    val assets: List<String>,
    val body:String,
)