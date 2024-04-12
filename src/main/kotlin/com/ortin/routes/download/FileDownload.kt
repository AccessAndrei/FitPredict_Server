package com.ortin.routes.download

import com.ortin.core.managers.RouteReservingManager.getSafetyFilesName
import com.ortin.plugins.generalCheck
import com.ortin.routes.models.IMAGE_PATH
import com.ortin.routes.models.MODEL_PATH
import com.ortin.routes.models.VIDEO_PATH
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.downloadModel() {
    get("/download/model/{id}") {
        generalCheck {
            val fileName = getSafetyFilesName(id = call.parameters["id"]).modelName
            requireNotNull(fileName) { "Model name not found in reserving system" }

            val imageFile = File("$MODEL_PATH/${fileName}")
            call.respondFileWithNotExists(imageFile)
        }
    }
}

fun Route.downloadImage() {
    get("/download/image/{id}") {
        generalCheck {
            val fileName = getSafetyFilesName(id = call.parameters["id"]).imageName
            requireNotNull(fileName) { "Image name not found in reserving system" }

            val imageFile = File("$IMAGE_PATH/$fileName")
            call.respondFileWithNotExists(imageFile)
        }
    }
}

fun Route.downloadVideo() {
    get("/download/video/{id}") {
        generalCheck {
            val fileName = getSafetyFilesName(id = call.parameters["id"]).videoName
            requireNotNull(fileName) { "Model name not found in reserving system" }

            val imageFile = File("$VIDEO_PATH/$fileName")
            call.respondFileWithNotExists(imageFile)
        }
    }
}

private suspend inline fun ApplicationCall.respondFileWithNotExists(file: File) {
    if (file.exists()) {
        respond(LocalFileContent(file))
    } else {
        respond(HttpStatusCode.NotFound)
    }
}
