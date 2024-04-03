package com.ortin.core.managers

import java.util.*

object RouteReservingManager {

    private val routeReserving: MutableMap<String, String?> = mutableMapOf()

    fun reserveRoute(): String {
        var id: String

        do {
            id = UUID.randomUUID().toString()
        } while (id !in routeReserving)
        routeReserving[id] = null

        return id
    }

    fun bindRoute(id: String, fileName: String) {
        routeReserving[id] = fileName
    }

    fun getReservedPath(id: String): String? {
        return routeReserving[id]
    }

    fun isReserved(id: String): Boolean {
        return id in routeReserving
    }
}
