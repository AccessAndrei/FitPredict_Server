package com.ortin.routes.models

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.uploadModel() {
    var fileName = ""

    post("/upload/{id}") {
        kotlin.runCatching {
            val id = call.parameters["id"]
            requireNotNull(id) { "Id must be require" }

            val contentLength = call.request.header(HttpHeaders.ContentLength)

            require(contentLength!!.toInt() <= MAX_MODEL_SIZE_BYTES) { "File size exceeds 200 MB limit" }

            val partData = call.receiveMultipart().readPart()
            requireNotNull(partData) { "File not found" }

            when (partData) {
                is PartData.FileItem -> {
                    fileName = partData.originalFileName?.let { name ->
                        "${id}.${name.substringAfterLast('.', "")}"
                    } ?: "${id}"

                    val fileBytes = partData.streamProvider().readBytes()
                    File("$UPLOAD_MODEL_PATH/$fileName").writeBytes(fileBytes)
                }

                else -> {}
            }
            partData.dispose()

            call.respondText("file uploaded to '$UPLOAD_MODEL_PATH/$fileName'")
        }.onFailure {
            call.respond(HttpStatusCode.BadRequest, it.message ?: "Something Went Wrong")
        }
    }
}

const val UPLOAD_PATH = "uploads"
const val UPLOAD_MODEL_PATH = "$UPLOAD_PATH/models"
const val MAX_MODEL_SIZE_BYTES = 200 * 1024 * 1024 // 200 MB
