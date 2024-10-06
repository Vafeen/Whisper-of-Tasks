package ru.vafeen.whisperoftasks.utils

import android.content.Context

fun getVersionName(context: Context): String? =
    context.packageManager.getPackageInfo(context.packageName, 0).versionName


