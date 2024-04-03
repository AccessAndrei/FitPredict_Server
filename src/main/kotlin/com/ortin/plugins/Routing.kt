package com.ortin.plugins

import com.ortin.routes.cards.getRegisterFitCard
import com.ortin.routes.models.uploadImage
import com.ortin.routes.models.uploadModel
import com.ortin.routes.models.uploadVideo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Application.configureRouting() {
    routing {
        /* ----- Upload ----- */
        uploadModel()
        uploadImage()
        uploadVideo()

        /* ----- RegisterFitCard ----- */
        getRegisterFitCard()

        staticResources("/models", "static/models") {
            preCompressed(CompressedFileType.GZIP)
        }
    }
}

suspend inline fun <R> PipelineContext<*, ApplicationCall>.generalCheck(body: () -> R) {
    runCatching(block = body).onFailure {
        call.respond(HttpStatusCode.BadRequest, it.message ?: "Something Went Wrong")
    }
}
