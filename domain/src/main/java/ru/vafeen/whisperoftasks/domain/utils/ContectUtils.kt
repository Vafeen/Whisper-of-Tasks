package ru.vafeen.whisperoftasks.domain.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.getVersionName(): String? =
    packageManager.getPackageInfo(packageName, 0).versionName

fun Context.copyTextToClipBoard(label: String, text: String) {
    val clipboard =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    if (text.isNotEmpty()) clipboard.setPrimaryClip(clip)
}

internal fun Context.pathToDownloadRelease(): String =
    "${externalCacheDir?.absolutePath}/app-release.apk"
