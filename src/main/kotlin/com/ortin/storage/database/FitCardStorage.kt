package com.ortin.storage.database

import com.ortin.models.CardFitEntity

object FitCardStorage {
    val cardList: MutableList<CardFitEntity> = mutableListOf()
}

fun <T> MutableList<T>.replace(entity: T, predicate: (T) -> Boolean): Boolean {
    return runCatching {
        val old = find(predicate)
        requireNotNull(old)
        require(remove(old))

        add(entity)
    }.getOrDefault(defaultValue = false)
}
