package com.ortin.routes.download

import com.ortin.plugins.generalCheck
import com.ortin.routes.models.UPLOAD_IMAGE_PATH
import com.ortin.routes.models.UPLOAD_MODEL_PATH
import com.ortin.routes.models.UPLOAD_VIDEO_PATH
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.downloadModel() {
    get("/download/model/{fileName}") {
        generalCheck {
            val fileName = call.parameters["fileName"]
            requireNotNull(fileName) { "FileName must be require" }

            val imageFile = File("$UPLOAD_MODEL_PATH/$fileName")
            call.respondFileWithNotExists(imageFile)
        }
    }
}

fun Route.downloadImage() {
    get("/download/image/{fileName}") {
        generalCheck {
            val fileName = call.parameters["fileName"]
            requireNotNull(fileName) { "FileName must be require" }

            val imageFile = File("$UPLOAD_IMAGE_PATH/$fileName")
            call.respondFileWithNotExists(imageFile)
        }
    }
}

fun Route.downloadVideo() {
    get("/download/video/{fileName}") {
        generalCheck {
            val fileName = call.parameters["fileName"]
            requireNotNull(fileName) { "FileName must be require" }

            val imageFile = File("$UPLOAD_VIDEO_PATH/$fileName")
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
