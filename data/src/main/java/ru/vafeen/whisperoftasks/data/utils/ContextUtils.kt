package ru.vafeen.whisperoftasks.data.utils

import android.content.Context

internal fun Context.pathToDownloadRelease(): String =
    "${externalCacheDir?.absolutePath}/app-release.apk"