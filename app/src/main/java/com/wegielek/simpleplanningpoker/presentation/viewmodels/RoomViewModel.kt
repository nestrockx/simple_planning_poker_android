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
import com.wegielek.simpleplanningpoker.domain.usecases.room.CreateRoomUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.room.GetRoomUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.room.JoinRoomUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.story.CreateStoryUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.story.DeleteStoryUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.story.GetStoriesUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.story.GetStoryUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.user.GetUserInfoUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.user.GetUserUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.vote.CreateVoteUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.vote.DeleteVoteUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.vote.GetVotesUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.websocket.WebSocketUseCase
import com.wegielek.simpleplanningpoker.prefs.Preferences
import com.wegielek.simpleplanningpoker.presentation.ui.views.room.RoomType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

private const val LOG_TAG = "RoomViewModel"
private const val REPEAT_LIMIT = 10

@HiltViewModel
class RoomViewModel
    @Inject
    constructor(
        private val getRoomUseCase: GetRoomUseCase,
        private val createStoryUseCase: CreateStoryUseCase,
        private val getStoriesUseCase: GetStoriesUseCase,
        private val getStoryUseCase: GetStoryUseCase,
        private val deleteStoryUseCase: DeleteStoryUseCase,
        private val getVotesUseCase: GetVotesUseCase,
        private val createRoomUseCase: CreateRoomUseCase,
        private val getUserInfoUseCase: GetUserInfoUseCase,
        private val getUserUseCase: GetUserUseCase,
        private val joinRoomUseCase: JoinRoomUseCase,
        private val createVoteUseCase: CreateVoteUseCase,
        private val deleteVoteUseCase: DeleteVoteUseCase,
        private val webSocketUseCase: WebSocketUseCase,
    ) : ViewModel() {
        private var repeat = 0

        var votingDialogVisible by mutableStateOf(false)

        var selectedVoteValue by mutableStateOf("")

        var isConnected: Boolean by mutableStateOf(false)
            private set

        var roomCode: String by mutableStateOf("")
            private set

        var roomNameField by mutableStateOf("")
            private set
        var roomCodeField by mutableStateOf("")
            private set
        var roomTypeField by mutableStateOf(RoomType.DEFAULT)
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

        // room code
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

        fun getStory(pk: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val story = getStoryUseCase(pk)
                    Log.d(LOG_TAG, "Story: $story")
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Error fetching story: ${e.message}")
                }
            }
        }

        // Websocket init
        fun connectWebSocket(context: Context) {
            viewModelScope.launch(Dispatchers.IO) {
                webSocketUseCase.isConnected.collect {
                    Log.d("WebSocket", "Connected: $it")
                    isConnected = it
                }
            }
            viewModelScope.launch(Dispatchers.IO) {
                webSocketUseCase.incomingMessages.collect {
                    Log.d("WebSocket", "Received message: $it")
                    _messages.value = _messages.value + it
                }
            }

            connect(context)
//            webSocketUseCase.connect(roomCode)
        }

        private fun connect(context: Context) {
            viewModelScope.launch(Dispatchers.IO) {
                if (!roomCode.isNotEmpty()) {
                    repeat += 1
                    if (repeat <= REPEAT_LIMIT) {
                        delay(100)
                        Log.d("Websocket", "Reconnecting...")
                        connect(context)
                    }
                } else {
                    repeat = 0
                    Preferences.getToken(context).collect { token ->
                        token?.let { webSocketUseCase.connect(roomCode, it) }
                    }
                }
            }
        }

        fun sendToWebsocket(text: String) {
            webSocketUseCase.sendMessage(text)
        }

        override fun onCleared() {
            super.onCleared()
            Log.d("Websocket", "onCleared")
            disconnectWebsocket()
        }

        fun disconnectWebsocket() {
            webSocketUseCase.disconnect()
        }

        // Update active story
        fun updateCurrentStory(story: Story) {
            currentStory = story
        }

        // Fetch room data and stories with room code
        fun fetchRoom() {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (roomCode.isNotEmpty()) {
                        Log.d(LOG_TAG, "Fetching room with code: $roomCode")
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

        // Fetch votes for current story
        fun fetchVotes() {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _votes.value = currentStory?.let { getVotesUseCase(it.id) }
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Error fetching votes: ${e.message}")
                }
            }
        }

        // New Story Input
        fun onNewStoryTitleChanged(newNewStoryTitle: String) {
            newStoryTitle = newNewStoryTitle
        }

        private fun clearNewStoryTitle() {
            newStoryTitle = ""
        }

        fun onRoomNameChanged(newRoomName: String) {
            roomNameField = newRoomName
        }

        fun onRoomCodeChanged(newRoomCode: String) {
            roomCodeField = newRoomCode
        }

        fun onRoomTypeChanged(newRoomType: RoomType) {
            roomTypeField = newRoomType
        }

        fun createRoom(
            name: String,
            type: String,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    Log.d(LOG_TAG, "Creating room with name: $name and type: $type")
                    val roomData: Room = createRoomUseCase(name, type)
                    createStoryUseCase(roomData.id, "Story")
                    roomCode = roomData.code
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Error creating room: ${e.message}")
                }
            }
        }

        // Handle stories
        fun createStory() {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (newStoryTitle.isNotEmpty()) {
                        val storyData: Story = createStoryUseCase(_room.value!!.id, newStoryTitle)
                        clearNewStoryTitle()
                        sendToWebsocket(
                            JSONObject()
                                .put("action", "add_story")
                                .put("story_id", storyData.id)
                                .put("title", storyData.title)
                                .toString(),
                        )
                    }
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Error creating story: ${e.message}")
                }
            }
        }

        fun deleteStory(story: Story) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    deleteStoryUseCase(story.id)
                    webSocketUseCase.sendMessage(
                        JSONObject()
                            .put("action", "remove_story")
                            .put("story_id", story.id)
                            .put("title", story.title)
                            .toString(),
                    )
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Error deleting story: ${e.message}")
                }
            }
        }

        fun joinRoom() {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    joinRoomUseCase(roomCode)
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Error joining room: ${e.message}")
                }
            }
        }

        fun joinRoom(
            code: String,
            onError: (String) -> Unit,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    joinRoomUseCase(code)
                    roomCode = code
                } catch (e: Exception) {
                    onError(e.message ?: "Unknown error")
                }
            }
        }

        // Handle participants
        fun addParticipant(participant: ParticipantUser) {
            _participants.value =
                (_participants.value ?: emptyList()).let { list ->
                    if (list.none { it.id == participant.id }) {
                        list + participant
                    } else {
                        Log.d(LOG_TAG, "Participant already exists: ${participant.profile.nickname}")
                        list
                    }
                }
        }

        fun removeParticipant(id: Int) {
            _participants.value = _participants.value?.filter { it.id != id }
        }

        fun getUserInfo() =
            viewModelScope.async {
                getUserInfoUseCase()
            }

        fun getUser(userId: Int) =
            viewModelScope.async {
                getUserUseCase(userId)
            }

        // Votes
        fun revealVotes(value: Boolean) {
            Log.d(LOG_TAG, "Revealing votes: $value")
            currentStory =
                currentStory?.copy(
                    isRevealed = value,
                )
        }

        fun clearVotes() {
            _votes.value = emptyList()
        }

        fun updateVote(vote: com.wegielek.simpleplanningpoker.domain.models.websocket.Vote) {
            fetchVotes()
//            if (_votes.value.isNullOrEmpty()) {
//            fetchVotes()
//                return
//            }

//            val participantUsernames = _participants.value?.map { it.username } ?: emptyList()
//
//            if (vote.username in participantUsernames) {
//                _votes.value =
//                    _votes.value?.map {
//                        if (it.user.username == vote.username) {
//                            it.copy(value = vote.value.toString().replace("\"", ""))
//                        } else {
//                            it
//                        }
//                    }
//            } else {
//                _votes.value = _votes.value?.plus(Vote(vote.value))
//            }
        }

        // Stories
        fun addStory(story: com.wegielek.simpleplanningpoker.domain.models.websocket.Story) {
            _stories.value =
                _room.value?.let {
                    (_stories.value ?: emptyList()) +
                        Story(
                            story.id,
                            it.id,
                            story.title,
                            false,
                            story.isRevealed,
                            "",
                        )
                }
        }

        fun removeStory(story: com.wegielek.simpleplanningpoker.domain.models.websocket.Story) {
            _stories.value = _stories.value?.filter { it.id != story.id }
            if (currentStory?.id == story.id && currentStory?.id != _stories.value?.get(0)?.id) {
                currentStory = _stories.value?.get(0)
            } else if (currentStory?.id == story.id) {
                currentStory = _stories.value?.get(1)
            }
        }

        fun summon(story: com.wegielek.simpleplanningpoker.domain.models.websocket.Story) {
            _room.value?.let {
                currentStory =
                    Story(
                        story.id,
                        it.id,
                        story.title,
                        true,
                        story.isRevealed,
                        "",
                    )
            }
        }

        fun showVotingDialog() {
            votingDialogVisible = true
        }

        fun hideVotingDialog() {
            votingDialogVisible = false
        }

        fun setVoteValue(value: String) {
            selectedVoteValue = value
        }

        fun updateUserVote() {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    currentStory?.let { createVoteUseCase(it.id, selectedVoteValue) }
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Error creating vote: ${e.message}")
                }
            }
            currentStory?.let {
                sendToWebsocket(
                    JSONObject()
                        .put("action", "vote")
                        .put("story_id", it.id)
                        .put("value", selectedVoteValue)
                        .toString(),
                )
            }
        }

        fun revealVotesSend(value: Boolean) {
            if (value) {
                sendToWebsocket(
                    JSONObject()
                        .put("action", "reveal")
                        .put("story_id", currentStory?.id)
                        .toString(),
                )
            } else {
                sendToWebsocket(
                    JSONObject()
                        .put("action", "unreveal")
                        .put("story_id", currentStory?.id)
                        .toString(),
                )
            }
        }

        fun resetVotes() {
            viewModelScope.launch(Dispatchers.IO) {
                currentStory?.id?.let { deleteVoteUseCase(it) }
            }
            _votes.value = _votes.value?.filter { false }
            sendToWebsocket(
                JSONObject()
                    .put("action", "reset")
                    .put("story_id", currentStory?.id)
                    .toString(),
            )
        }
    }
