package ru.vafeen.whisperoftasks.domain.domain_models


import androidx.compose.ui.graphics.Color
import com.google.gson.Gson

data class Settings(
    val lightThemeColor: Color? = null,
    val darkThemeColor: Color? = null,
    val lastDemonstratedVersion: Int? = null,
    val catInSettings: Boolean = false,
    val releaseBody: String? = null
) {
    fun toJsonString(): String = Gson().toJson(this)
}
