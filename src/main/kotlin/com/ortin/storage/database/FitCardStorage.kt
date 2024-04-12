package com.ortin.storage.database

import com.ortin.models.CardFitEntity

object FitCardStorage {
    val cardList: MutableList<CardFitEntity> = mutableListOf()
}

fun <T> MutableList<T>.replace(entity: T, predicate: (T) -> Boolean) {
    val old = find(predicate)
    old?.let {
        remove(old)
        add(entity)
    }
}
