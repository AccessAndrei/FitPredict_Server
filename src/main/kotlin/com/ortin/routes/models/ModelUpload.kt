package com.ortin.routes.models

import com.ortin.core.managers.RouteReservingManager.bindRoute
import com.ortin.core.managers.RouteReservingManager.getSafetyFilesName
import com.ortin.core.managers.RouteReservingManager.isReserved
import com.ortin.models.FilesInfo
import com.ortin.plugins.generalCheck
import com.ortin.storage.repository.FitCardRepository
import com.ortin.storage.repository.FitCardRepositoryImpl
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import java.io.File

private val cardRepository: FitCardRepository = FitCardRepositoryImpl(Dispatchers.IO)

fun Route.uploadModel() {
    post("/upload/model/{id}") {
        generalCheck {
            val id = checkId(call.parameters["id"])
            val modelVersion = getSafetyFilesName(id = id).modelVersion + 1

            val contentLength = call.request.header(HttpHeaders.ContentLength)
            require(contentLength!!.toInt() <= MAX_MODEL_SIZE_BYTES) { "File size exceeds ${MAX_MODEL_SIZE_BYTES.toMB()} MB limit" }

            val fileName =
                call.receiveMultipart().uploadFile(fileName = "model_${id}_version_$modelVersion", path = MODEL_PATH)
            bindRoute(id = id, filesInfo = FilesInfo(modelName = fileName, modelVersion = modelVersion))

            val newCard = cardRepository.getCardById(id).copy(modelVersionCode = modelVersion)
            cardRepository.updateCard(newCard)

            call.respondText("file uploaded to '$MODEL_PATH/$fileName'")
        }
    }
}

fun Route.uploadImage() {
    post("/upload/image/{id}") {
        generalCheck {
            val id = checkId(call.parameters["id"])
            val imageVersion = getSafetyFilesName(id = id).imageVersion + 1

            val contentLength = call.request.header(HttpHeaders.ContentLength)
            require(contentLength!!.toInt() <= MAX_IMAGE_SIZE_BYTES) { "File size exceeds ${MAX_IMAGE_SIZE_BYTES.toMB()} MB limit" }

            val fileName =
                call.receiveMultipart().uploadFile(fileName = "image_${id}_version_$imageVersion", path = IMAGE_PATH)
            bindRoute(id = id, filesInfo = FilesInfo(imageName = fileName, imageVersion = imageVersion))

            val newCard = cardRepository.getCardById(id).copy(imageVersionCode = imageVersion)
            cardRepository.updateCard(newCard)

            call.respondText("file uploaded to '$IMAGE_PATH/$fileName'")
        }
    }
}

fun Route.uploadVideo() {
    post("/upload/video/{id}") {
        generalCheck {
            val id = checkId(call.parameters["id"])
            val videoVersion = getSafetyFilesName(id = id).videoVersion + 1

            val contentLength = call.request.header(HttpHeaders.ContentLength)
            require(contentLength!!.toInt() <= MAX_VIDEO_SIZE_BYTES) { "File size exceeds ${MAX_VIDEO_SIZE_BYTES.toMB()} MB limit" }

            val fileName =
                call.receiveMultipart().uploadFile(fileName = "video_${id}_version_$videoVersion", path = VIDEO_PATH)
            bindRoute(id = id, filesInfo = FilesInfo(videoName = fileName, videoVersion = videoVersion))

            val newCard = cardRepository.getCardById(id).copy(videoVersionCode = videoVersion)
            cardRepository.updateCard(newCard)

            call.respondText("file uploaded to '$VIDEO_PATH/$fileName'")
        }
    }
}

private suspend fun MultiPartData.uploadFile(fileName: String, path: String): String {
    val partData = readPart()
    requireNotNull(partData) { "File not found" }

    var resultFileName = ""
    when (partData) {
        is PartData.FileItem -> {
            resultFileName = partData.originalFileName?.let { name ->
                "${fileName}.${name.substringAfterLast('.', "")}"
            } ?: fileName

            val fileBytes = partData.streamProvider().readBytes()
            File("$path/$resultFileName").writeBytes(fileBytes)
        }

        else -> {}
    }
    partData.dispose()

    return resultFileName
}

private fun checkId(id: String?): String {
    requireNotNull(id) { "Id must be require" }
    require(isReserved(id = id)) { "Id is not reserved" }

    return id
}

private fun Int.toMB(): Int = this / 1024 / 1024

const val UPLOAD_PATH = "uploads"
const val MODEL_PATH = "$UPLOAD_PATH/models"
const val IMAGE_PATH = "$UPLOAD_PATH/images"
const val VIDEO_PATH = "$UPLOAD_PATH/videos"
const val MAX_MODEL_SIZE_BYTES = 200 * 1024 * 1024 // 200 MB
const val MAX_IMAGE_SIZE_BYTES = 2 * 1024 * 1024 // 2 MB
const val MAX_VIDEO_SIZE_BYTES = 250 * 1024 * 1024 // 250 MB
