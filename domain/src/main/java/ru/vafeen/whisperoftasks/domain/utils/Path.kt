package ru.vafeen.whisperoftasks.domain.utils

import android.content.Context

object Path {
    val path = { context: Context -> "${context.externalCacheDir?.absolutePath}/app-release.apk" }
}