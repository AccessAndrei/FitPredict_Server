package com.ortin.plugins

import com.ortin.routes.models.uploadModel
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        uploadModel()

        staticResources("/models", "static/models") {
            preCompressed(CompressedFileType.GZIP)
        }
    }
}
