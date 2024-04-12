package com.ortin.storage.repository

import com.ortin.models.CardFit
import com.ortin.models.CardFitEntity
import com.ortin.models.mapToModel
import com.ortin.models.mapToStorage
import com.ortin.storage.database.FitCardStorage
import com.ortin.storage.database.replace
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface FitCardRepository {

    suspend fun createCard(cardFit: CardFit)
    suspend fun updateCard(cardFit: CardFit)
    suspend fun removeCard(cardFit: CardFit)
    suspend fun getAllAvailableCards(): List<CardFit>
    suspend fun getCardById(cardId: String): CardFit
}

class FitCardRepositoryImpl(
    val dispatcher: CoroutineDispatcher
) : FitCardRepository {

    val cardStorage = FitCardStorage.cardList

    override suspend fun createCard(cardFit: CardFit) {
        withContext(dispatcher) {
            cardStorage.add(cardFit.mapToStorage().copy(isActive = false))
        }
    }

    override suspend fun updateCard(cardFit: CardFit) {
        withContext(dispatcher) {
            val entity = cardFit.mapToStorage()

            val isActive = runCatching {
                with(entity) {
                    requireNotNull(modelVersionCode)
                    requireNotNull(imageVersionCode)
                    requireNotNull(videoVersionCode)
                    true
                }
            }.getOrDefault(defaultValue = false)

            cardStorage.replace(entity = entity.copy(isActive = isActive)) { card ->
                card.cardId == entity.cardId
            }
        }
    }

    override suspend fun removeCard(cardFit: CardFit) {
        cardStorage.removeIf { card -> card.cardId == cardFit.cardId }
    }

    override suspend fun getAllAvailableCards(): List<CardFit> {
        return cardStorage.filter { card -> card.isActive }.map(CardFitEntity::mapToModel)
    }

    override suspend fun getCardById(cardId: String): CardFit {
        return cardStorage.first { card -> card.cardId == cardId }.mapToModel()
    }
}
