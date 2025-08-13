package com.wegielek.simpleplanningpoker.domain.repository

import com.wegielek.simpleplanningpoker.domain.models.websocket.WebSocketMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WebSocketRepository {
    val incomingMessages: Flow<WebSocketMessage>

    fun connect()

    fun disconnect()

    fun sendMessage(message: String)
}
