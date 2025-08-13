package com.wegielek.simpleplanningpoker.data.remote

import android.content.Context
import com.wegielek.simpleplanningpoker.data.models.websocket.WebSocketMessageDto
import com.wegielek.simpleplanningpoker.prefs.Preferences.Companion.getTokenFromStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
        val isConnected: StateFlow<Boolean> = _isConnected

        private var webSocket: WebSocket? = null

        fun connect(url: String) {
            println("websocket " + _isConnected.value)
            if (_isConnected.value) return

            val client = OkHttpClient()
            val request =
                Request
                    .Builder()
                    .url(url)
                    .addHeader("Authorization", "Token 86babdcbb9cf17facd8248c2c47d7d2dba8f500f")
                    .build()
//            ${getTokenFromStorage(context)}
            webSocket = client.newWebSocket(request, this)
        }

        fun disconnect() {
            webSocket?.close(1000, "Closing")
            _isConnected.value = false
        }

        fun sendMessage(text: String) {
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
