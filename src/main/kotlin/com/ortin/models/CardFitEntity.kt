package com.ortin.models

import kotlinx.serialization.Serializable

@Serializable
data class CardFitEntity(
    val cardId: String,
    val nameFit: String,
    val description: String,
    val modelVersionCode: Int?,
    val imageVersionCode: Int?,
    val videoVersionCode: Int?,
    val isActive: Boolean,
)
