package com.ortin.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterFitCardModel(
    val nameFit: String,
    val description: String
)
