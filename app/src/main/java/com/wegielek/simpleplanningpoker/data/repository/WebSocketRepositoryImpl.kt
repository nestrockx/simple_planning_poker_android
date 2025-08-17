package com.wegielek.simpleplanningpoker.data.repository

import com.wegielek.simpleplanningpoker.data.models.websocket.toDomain
import com.wegielek.simpleplanningpoker.data.remote.WebSocketService
import com.wegielek.simpleplanningpoker.domain.models.websocket.WebSocketMessage
import com.wegielek.simpleplanningpoker.domain.repository.WebSocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WebSocketRepositoryImpl
    @Inject
    constructor(
        private val webSocketService: WebSocketService,
    ) : WebSocketRepository {
        override val incomingMessages: Flow<WebSocketMessage> = webSocketService.messages.map { it.toDomain() }

        override val isConnected: Flow<Boolean> = webSocketService.isConnected

        override fun connect(
            roomCode: String,
            token: String,
        ) = webSocketService.connect(
            "wss://simple-planning-poker.onrender.com/ws/reveal/$roomCode/",
            token,
        )

        override fun disconnect() = webSocketService.disconnect()

        override fun sendMessage(message: String) = webSocketService.sendMessage(message)
    }
