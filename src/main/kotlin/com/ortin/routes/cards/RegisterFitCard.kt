package com.ortin.routes.cards

import com.ortin.models.CardFitEntity
import com.ortin.models.RegisterFitCardModel
import io.ktor.server.routing.*
import com.ortin.plugins.generalCheck
import com.ortin.storage.FitCardStorage
import io.ktor.server.application.*
import io.ktor.server.request.*
import java.util.UUID

fun Route.getRegisterFitCard() {
    get("/cards/registerfitcard") {
        generalCheck {
            val fitCard = call.receive<RegisterFitCardModel>()
            FitCardStorage.cardList.add(
                CardFitEntity(
                    nameFit = fitCard.nameFit,
                    description = fitCard.description,
                    imageRoute = "${UUID.randomUUID()}",
                    videoRoute = "${UUID.randomUUID()}",
                    modelRoute = "${UUID.randomUUID()}"
                )
            )
        }
    }
}
