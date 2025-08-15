package com.wegielek.simpleplanningpoker.domain.repository

import com.wegielek.simpleplanningpoker.domain.models.websocket.WebSocketMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WebSocketRepository {
    val incomingMessages: Flow<WebSocketMessage>

    val isConnected: Flow<Boolean>

    fun connect(roomCode: String)

    fun disconnect()

    fun sendMessage(message: String)
}
