package com.ortin.core.managers

import com.ortin.models.FilesInfo
import com.ortin.models.merge
import java.util.*

object RouteReservingManager {

    private val routeReserving: MutableMap<String, FilesInfo> = mutableMapOf()

    fun reserveCard(): String {
        var id: String

        do {
            id = UUID.randomUUID().toString()
            println("New ID: $id")
        } while (id in routeReserving)
        routeReserving[id] = FilesInfo()

        return id
    }

    fun removeReserve(id: String): Boolean {
        return runCatching {
            require(isReserved(id = id))
            requireNotNull(routeReserving.remove(id))
            true
        }.getOrDefault(false)
    }

    fun bindRoute(id: String, filesInfo: FilesInfo) {
        routeReserving[id] = routeReserving[id]!!.merge(filesInfo)
    }

    fun getReservedPath(id: String): FilesInfo? {
        return routeReserving[id]
    }

    fun isReserved(id: String): Boolean {
        return id in routeReserving
    }

    fun getSafetyFilesName(id: String?): FilesInfo {
        requireNotNull(id) { "Id must be require" }
        require(isReserved(id = id)) { "Id is not reserved" }

        val filesName = getReservedPath(id = id)
        requireNotNull(filesName) { "Path not found in reserving system" }

        return filesName
    }
}
