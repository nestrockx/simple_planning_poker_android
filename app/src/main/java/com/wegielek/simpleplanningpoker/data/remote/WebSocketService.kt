package com.wegielek.simpleplanningpoker.data.remote

import android.content.Context
import com.wegielek.simpleplanningpoker.data.models.websocket.WebSocketMessageDto
import com.wegielek.simpleplanningpoker.prefs.Preferences.Companion.getTokenFromStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
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

        private var webSocket: WebSocket? = null

        fun connect(url: String) {
            val client = OkHttpClient()
            val request =
                Request
                    .Builder()
                    .url(url)
                    .addHeader("Authorization", "Token ${getTokenFromStorage(context)}")
                    .build()
            webSocket = client.newWebSocket(request, this)
        }

        fun disconnect() {
            webSocket?.close(1000, "Closing")
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

        override fun onFailure(
            webSocket: WebSocket,
            t: Throwable,
            response: Response?,
        ) {
            println("WebSocket error: ${t.message}")
        }

        override fun onClosed(
            webSocket: WebSocket,
            code: Int,
            reason: String,
        ) {
            println("WebSocket closed: $reason")
        }
    }
