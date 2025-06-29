package com.wegielek.simpleplanningpoker.domain.usecases.websocket

import com.wegielek.simpleplanningpoker.domain.repository.WebSocketRepository

class WebSocketUseCase(
    private val repository: WebSocketRepository,
) {
    val incomingMessages = repository.incomingMessages

    fun connect() = repository.connect()

    fun disconnect() = repository.disconnect()

    fun sendMessage(message: String) = repository.sendMessage(message)
}
