package com.ortin.routes.cards

import com.ortin.core.managers.RouteReservingManager
import com.ortin.models.CardFit
import com.ortin.plugins.generalCheck
import com.ortin.storage.repository.FitCardRepository
import com.ortin.storage.repository.FitCardRepositoryImpl
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers

private val cardRepository: FitCardRepository = FitCardRepositoryImpl(Dispatchers.IO)

fun Route.changeFitCard() {
    put("/cards") {
        generalCheck {
            val fitCard = call.receive<CardFit>()
            require(cardRepository.updateCard(fitCard)) { "Card not found" }
            call.respondText { "Card was changed" }
        }
    }
}

fun Route.removeFitCard() {
    delete("/cards/{id}") {
        generalCheck {
            val card = getCardWithCheck(call.parameters["id"])
            require(cardRepository.removeCard(card)) { "Not found card" }
            require(RouteReservingManager.removeReserve(card.cardId)) { "Not found card in reserving system" }
            call.respondText { "Card was removed" }
        }
    }
}

private suspend fun getCardWithCheck(cardId: String?): CardFit {
    requireNotNull(cardId) { "Id must be require" }

    val card = cardRepository.getCardById(cardId)
    requireNotNull(card) { "Card not found" }

    return card
}
