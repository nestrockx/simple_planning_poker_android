package com.wegielek.simpleplanningpoker.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.room.Room
import com.wegielek.simpleplanningpoker.domain.models.room.Story
import com.wegielek.simpleplanningpoker.domain.models.room.Vote
import com.wegielek.simpleplanningpoker.domain.models.websocket.WebSocketMessage
import com.wegielek.simpleplanningpoker.domain.usecases.CreateRoomUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.CreateStoryUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.GetRoomUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.GetStoriesUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.GetVotesUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.websocket.WebSocketUseCase
import com.wegielek.simpleplanningpoker.prefs.Preferences
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
        private val createStoryUseCase: CreateStoryUseCase,
        private val getStoriesUseCase: GetStoriesUseCase,
        private val getVotesUseCase: GetVotesUseCase,
        private val createRoomUseCase: CreateRoomUseCase,
        private val webSocketUseCase: WebSocketUseCase,
    ) : ViewModel() {
        companion object {
            private const val LOG_TAG = "RoomViewModel"
        }

        var roomNameField by mutableStateOf("")
            private set

        var roomCode: String by mutableStateOf("")
            private set

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
        private val _participants = MutableStateFlow<List<ParticipantUser>?>(null)
        val participants: StateFlow<List<ParticipantUser>?> = _participants

        private val _messages = MutableStateFlow<List<WebSocketMessage>>(emptyList())
        val messages: StateFlow<List<WebSocketMessage>> = _messages.asStateFlow()

        init {
            fetchRoom()
//            connectWebSocket()
        }

        fun getRoomCode(context: Context): String {
            if (roomCode.isNotEmpty() && Preferences.getRoomCodeFromStorage(context) != null) return roomCode

            if (Preferences.getRoomCodeFromStorage(context) != null) {
                roomCode = Preferences.getRoomCodeFromStorage(context)!!
            } else if (roomCode != "") {
                saveRoomCode(context, roomCode)
            }
            return roomCode
        }

        fun saveRoomCode(
            context: Context,
            roomCode: String,
        ) {
            this.roomCode = roomCode
            Preferences.saveRoomCodeToStorage(context, roomCode)
        }

        fun clearRoomCode(context: Context) {
            roomCode = ""
            Preferences.clearRoomCodeFromStorage(context)
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
                try {
                    if (roomCode.isNotEmpty()) {
                        _room.value = getRoomUseCase(roomCode)
                    }
                } catch (
                    e: Exception,
                ) {
                    Log.e(
                        LOG_TAG,
                        "Error fetching room: ${e.message}",
                    )
                }
                _participants.value = _room.value?.participants
                try {
                    _stories.value = _room.value?.let { getStoriesUseCase(it.id) }
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Error fetching stories: ${e.message}")
                }

                _stories.value?.size?.let {
                    if (it > 0) {
                        currentStory = _stories.value?.get(0)
                    }
                }
                Log.d(LOG_TAG, "Stories: ${_stories.value}")
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

        fun onRoomNameChanged(newRoomName: String) {
            roomNameField = newRoomName
        }

        fun createRoom(
            name: String,
            type: String,
        ) {
            viewModelScope.launch {
                try {
                    Log.d(LOG_TAG, "Creating room with name: $name and type: $type")
                    val roomData: Room = createRoomUseCase(name, type)
                    val storyData: Story = createStoryUseCase(roomData.id)
                    roomCode = roomData.code
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Error creating room: ${e.message}")
                }
            }
        }
    }
