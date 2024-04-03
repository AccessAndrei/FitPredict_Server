package com.ortin.routes.download

import com.ortin.core.managers.RouteReservingManager.getReservedPath
import com.ortin.core.managers.RouteReservingManager.isReserved
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
            val fileName = getFileNameInReservingManager(id = call.parameters["id"])
            val imageFile = File("$MODEL_PATH/${fileName}")
            call.respondFileWithNotExists(imageFile)
        }
    }
}

fun Route.downloadImage() {
    get("/download/image/{id}") {
        generalCheck {
            val fileName = getFileNameInReservingManager(id = call.parameters["id"])
            val imageFile = File("$IMAGE_PATH/$fileName")
            call.respondFileWithNotExists(imageFile)
        }
    }
}

fun Route.downloadVideo() {
    get("/download/video/{id}") {
        generalCheck {
            val fileName = getFileNameInReservingManager(id = call.parameters["id"])
            val imageFile = File("$VIDEO_PATH/$fileName")
            call.respondFileWithNotExists(imageFile)
        }
    }
}

private fun getFileNameInReservingManager(id: String?): String {
    requireNotNull(id) { "Id must be require" }
    require(isReserved(id = id)) { "Id is not reserved" }

    val fileName = getReservedPath(id = id)
    requireNotNull(fileName) { "Path not found in reserving system" }

    return fileName
}

private suspend inline fun ApplicationCall.respondFileWithNotExists(file: File) {
    if (file.exists()) {
        respond(LocalFileContent(file))
    } else {
        respond(HttpStatusCode.NotFound)
    }
}
