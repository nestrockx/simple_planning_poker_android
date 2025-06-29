package com.wegielek.simpleplanningpoker.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wegielek.simpleplanningpoker.domain.models.room.Room
import com.wegielek.simpleplanningpoker.domain.models.room.Story
import com.wegielek.simpleplanningpoker.domain.models.room.Vote
import com.wegielek.simpleplanningpoker.domain.models.websocket.WebSocketMessage
import com.wegielek.simpleplanningpoker.domain.usecases.GetRoomUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.GetStoriesUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.GetVotesUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.websocket.WebSocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel
    @Inject
    constructor(
        private val getRoomUseCase: GetRoomUseCase,
        private val getStoriesUseCase: GetStoriesUseCase,
        private val getVotesUseCase: GetVotesUseCase,
        private val webSocketUseCase: WebSocketUseCase,
    ) : ViewModel() {
        var newStoryTitle: String by mutableStateOf("")
            private set
        var currentStory: Story? by mutableStateOf(null)
            private set

        private val _room = MutableStateFlow<Room?>(null)
        val room: StateFlow<Room?> = _room
        private val _stories = MutableStateFlow<List<Story>?>(null)
        val stories: StateFlow<List<Story>?> = _stories
        private val _votes = MutableStateFlow<List<Vote>?>(null)
        val votes: StateFlow<List<Vote>?> = _votes

        private val _messages = MutableStateFlow<List<WebSocketMessage>>(emptyList())
        val messages: StateFlow<List<WebSocketMessage>> = _messages.asStateFlow()

        init {
            fetchRoom()
//            connectWebSocket()
        }

        private fun connectWebSocket() {
            viewModelScope.launch {
                webSocketUseCase.incomingMessages.collect {
                    Log.d("WebSocket", "Received message: $it")
                    _messages.value = _messages.value + it
                }
            }

            webSocketUseCase.connect()
        }

        fun send(text: String) {
            webSocketUseCase.sendMessage(text)
        }

        override fun onCleared() {
            super.onCleared()
            webSocketUseCase.disconnect()
        }

        fun updateCurrentStory(story: Story) {
            currentStory = story
        }

        fun fetchRoom() {
            viewModelScope.launch {
                _room.value = getRoomUseCase("JYeokJ")
                _stories.value = _room.value?.let { getStoriesUseCase(it.id) }
                currentStory = _stories.value?.get(0)
            }
        }

        fun fetchVotes() {
            viewModelScope.launch {
                _votes.value = currentStory?.let { getVotesUseCase(it.id) }
            }
        }

        fun onNewStoryTitleChanged(newNewStoryTitle: String) {
            newStoryTitle = newNewStoryTitle
        }
    }
