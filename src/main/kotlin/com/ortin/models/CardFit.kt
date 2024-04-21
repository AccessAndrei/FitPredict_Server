package com.ortin.models

import kotlinx.serialization.Serializable

@Serializable
data class CardFit(
    val cardId: String,
    val nameFit: String,
    val description: String,
    val modelVersionCode: Int?,
    val imageVersionCode: Int?,
    val videoVersionCode: Int?,
)

fun CardFit.mapToStorage(): CardFitEntity =
    CardFitEntity(
        cardId = cardId,
        nameFit = nameFit,
        description = description,
        modelVersionCode = modelVersionCode,
        imageVersionCode = imageVersionCode,
        videoVersionCode = videoVersionCode,
        isActive = false // Stub property
    )

fun CardFitEntity.mapToModel(): CardFit =
    CardFit(
        cardId = cardId,
        nameFit = nameFit,
        description = description,
        modelVersionCode = modelVersionCode,
        imageVersionCode = imageVersionCode,
        videoVersionCode = videoVersionCode,
    )
