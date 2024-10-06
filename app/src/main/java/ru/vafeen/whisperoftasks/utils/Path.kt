package ru.vafeen.whisperoftasks.utils

import android.content.Context

object Path {
    val path = { context: Context -> "${context.externalCacheDir?.absolutePath}/app-release.apk" }
}