package com.ortin.models

import kotlinx.serialization.Serializable

@Serializable
data class CardFitEntity(
    val nameFit: String,
    val description: String,
    val imageRoute: String,
    val videoRoute: String,
    val modelRoute: String
)
