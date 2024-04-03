package com.ortin.routes.cards

import com.ortin.GENERAL_SERVER_ROUTE
import com.ortin.core.managers.RouteReservingManager.reserveRoute
import com.ortin.models.CardFitEntity
import com.ortin.models.RegisterFitCardModel
import com.ortin.models.RouteInfo
import com.ortin.plugins.generalCheck
import com.ortin.storage.FitCardStorage
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getRegisterFitCard() {
    get("/cards/registerfitcard") {
        generalCheck {
            val fitCard = call.receive<RegisterFitCardModel>()
            val generalRouteForDownload = "$GENERAL_SERVER_ROUTE/download"
            val generalRouteForUpload = "$GENERAL_SERVER_ROUTE/upload"

            val modelRoute = reserveRoute()
            val imageRoute = reserveRoute()
            val videoRoute = reserveRoute()

            FitCardStorage.cardList.add(
                CardFitEntity(
                    nameFit = fitCard.nameFit,
                    description = fitCard.description,
                    modelRoute = "$generalRouteForDownload/model/$modelRoute",
                    imageRoute = "$generalRouteForDownload/image/$imageRoute",
                    videoRoute = "$generalRouteForDownload/video/$videoRoute",
                )
            )

            call.respond(
                RouteInfo(
                    routeForModel = "$generalRouteForUpload/model/$modelRoute",
                    routeForImage = "$generalRouteForUpload/image/$imageRoute",
                    routeForVideo = "$generalRouteForUpload/video/$videoRoute",
                )
            )
        }
    }
}
