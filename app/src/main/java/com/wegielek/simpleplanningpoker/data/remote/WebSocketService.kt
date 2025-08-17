package com.wegielek.simpleplanningpoker.data.remote

import android.content.Context
import android.util.Log
import com.wegielek.simpleplanningpoker.data.models.websocket.WebSocketMessageDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class WebSocketService
    @Inject
    constructor(
        @param:ApplicationContext private val context: Context,
    ) : WebSocketListener() {
        private val _messages = MutableSharedFlow<WebSocketMessageDto>()
        val messages: SharedFlow<WebSocketMessageDto> = _messages.asSharedFlow()

        private val _isConnected = MutableStateFlow(false)
        val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

        private var webSocket: WebSocket? = null

        fun connect(
            url: String,
            token: String,
        ) {
            println("Websocket: " + _isConnected.value)
            if (_isConnected.value) return

            val client = OkHttpClient()
            val request =
                Request
                    .Builder()
                    .url(url)
                    .addHeader("Authorization", "Token $token")
                    .build()
            webSocket = client.newWebSocket(request, this)
        }

        fun disconnect() {
            if (!_isConnected.value) return
            webSocket?.close(1000, "Closing")
            _isConnected.value = false
        }

        fun sendMessage(text: String) {
            Log.d("Websocket", "Sending message: $text")
            webSocket?.send(text)
        }

        override fun onMessage(
            webSocket: WebSocket,
            text: String,
        ) {
            val json =
                Json {
                    ignoreUnknownKeys = true
                    classDiscriminator = "type"
                }
            Log.d("WebSocket", "Received message: $text")
            val dto = json.decodeFromString(WebSocketMessageDto.serializer(), text)
//            _messages.tryEmit(dto)
            CoroutineScope(Dispatchers.IO).launch {
                _messages.emit(dto)
            }
        }

        override fun onOpen(
            webSocket: WebSocket,
            response: Response,
        ) {
            println("WebSocket connected")
            _isConnected.value = true
        }

        override fun onFailure(
            webSocket: WebSocket,
            t: Throwable,
            response: Response?,
        ) {
            println("WebSocket error: ${t.message}")
            _isConnected.value = false
        }

        override fun onClosed(
            webSocket: WebSocket,
            code: Int,
            reason: String,
        ) {
            println("WebSocket closed: $reason")
            _isConnected.value = false
        }
    }
