package com.wegielek.simpleplanningpoker.domain.repository

import com.wegielek.simpleplanningpoker.domain.models.websocket.WebSocketMessage
import kotlinx.coroutines.flow.Flow

interface WebSocketRepository {
    val incomingMessages: Flow<WebSocketMessage>

    fun connect()

    fun disconnect()

    fun sendMessage(message: String)
}
