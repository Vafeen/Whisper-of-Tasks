package ru.vafeen.whisperoftasks.data.network.impl

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import ru.vafeen.whisperoftasks.data.utils.pathToDownloadRelease
import ru.vafeen.whisperoftasks.domain.network.Installer
import java.io.File

internal class InstallerImpl(private val context: Context) : Installer {
    override fun installApk() {
        val apkFilePath = context.pathToDownloadRelease()
        // Создаем объект File для APK файла по указанному пути
        val file = File(apkFilePath)

        // Проверяем, существует ли файл
        if (file.exists()) {
            // Создаем Intent для установки APK
            val intent = Intent(Intent.ACTION_VIEW).apply {
                // Устанавливаем URI и MIME-тип для файла
                setDataAndType(
                    FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.provider", // Указываем авторитет FileProvider
                        file
                    ),
                    "application/vnd.android.package-archive" // MIME-тип для APK файлов
                )
                // Добавляем флаг для предоставления разрешения на чтение URI
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                // Добавляем флаг для запуска новой задачи
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            // Запускаем активность для установки APK
            context.startActivity(intent)
        } else {
            // Логируем ошибку, если файл не существует
            Log.e("InstallApk", "APK file does not exist: $apkFilePath")
        }
    }
}