package com.ortin.routes.cards

import com.ortin.core.managers.RouteReservingManager
import com.ortin.models.CardFit
import com.ortin.models.RegisterFitCardModel
import com.ortin.plugins.generalCheck
import com.ortin.storage.repository.FitCardRepository
import com.ortin.storage.repository.FitCardRepositoryImpl
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers

private val cardRepository: FitCardRepository = FitCardRepositoryImpl(Dispatchers.IO)

fun Route.registerFitCard() {
    post("/cards/registerfitcard") {
        generalCheck {
            val fitCard = call.receive<RegisterFitCardModel>()
            val cardId = RouteReservingManager.reserveCard()

            val isCreated = cardRepository.createCard(
                CardFit(
                    cardId = cardId,
                    nameFit = fitCard.nameFit,
                    description = fitCard.description,
                    modelVersionCode = null,
                    imageVersionCode = null,
                    videoVersionCode = null,
                )
            )
            require(isCreated) { "Card not created" }

            call.respondText { cardId }
        }
    }
}
