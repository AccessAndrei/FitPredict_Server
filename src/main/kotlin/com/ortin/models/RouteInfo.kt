package com.ortin.models

import kotlinx.serialization.Serializable

@Serializable
data class RouteInfo(
    val routeForModel: String,
    val routeForImage: String,
    val routeForVideo: String
)
