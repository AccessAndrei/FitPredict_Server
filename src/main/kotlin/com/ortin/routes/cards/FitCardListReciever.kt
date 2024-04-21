package com.ortin.routes.cards

import com.ortin.plugins.generalCheck
import com.ortin.storage.database.FitCardStorage
import com.ortin.storage.repository.FitCardRepository
import com.ortin.storage.repository.FitCardRepositoryImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers

private val cardRepository: FitCardRepository = FitCardRepositoryImpl(Dispatchers.IO)

fun Route.getAllFitCards() {
    get("/cards/cardList") {
        generalCheck {
            call.respond(cardRepository.getAllAvailableCards())
        }
    }
}
