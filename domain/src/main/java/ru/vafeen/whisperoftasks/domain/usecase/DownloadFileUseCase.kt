package ru.vafeen.whisperoftasks.domain.usecase
//
//import okhttp3.ResponseBody
//import retrofit2.Call
//import retrofit2.http.Url
//import ru.vafeen.whisperoftasks.data.network.service.DownloadService
//import ru.vafeen.whisperoftasks.domain.network.DownloadFileRepository
//
//internal class DownloadFileUseCase(private val downloadFileRepository: DownloadFileRepository) {
//    operator fun invoke( fileUrl: String): Call<ResponseBody>? = try {
//        downloadService.downloadFile(fileUrl = fileUrl)
//    } catch (e: Exception) {
//        null
//    }
//}