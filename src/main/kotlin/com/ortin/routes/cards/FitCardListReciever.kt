package com.ortin.routes.cards

import com.ortin.plugins.generalCheck
import com.ortin.storage.FitCardStorage
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllFitCards() {
    get("/cards/cardList") {
        generalCheck {
            call.respond(FitCardStorage.cardList)
        }
    }
}
