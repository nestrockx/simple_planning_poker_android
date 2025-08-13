package com.wegielek.simpleplanningpoker.presentation.ui.views.room

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wegielek.simpleplanningpoker.domain.models.websocket.AddStory
import com.wegielek.simpleplanningpoker.domain.models.websocket.ParticipantAdd
import com.wegielek.simpleplanningpoker.domain.models.websocket.ParticipantRemove
import com.wegielek.simpleplanningpoker.domain.models.websocket.RemoveStory
import com.wegielek.simpleplanningpoker.domain.models.websocket.ResetVotes
import com.wegielek.simpleplanningpoker.domain.models.websocket.RevealVotes
import com.wegielek.simpleplanningpoker.domain.models.websocket.Summon
import com.wegielek.simpleplanningpoker.domain.models.websocket.VoteUpdate
import com.wegielek.simpleplanningpoker.presentation.viewmodels.RoomViewModel
import org.json.JSONObject

@Composable
fun RoomScreen(
    viewModel: RoomViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
) {
    val logTag = "RoomScreen"
    val context = LocalContext.current

    LaunchedEffect(viewModel.roomCode) {
        Log.d(logTag, "Room code: ${viewModel.roomCode}")
        viewModel.getRoomCode(context)
        viewModel.fetchRoom()
    }

    if (viewModel.roomCode.isNotEmpty()) {
        val room = viewModel.room.collectAsState()
        val stories = viewModel.stories.collectAsState()
        val votes = viewModel.votes.collectAsState()
        val participants = viewModel.participants.collectAsState()
        val messages = viewModel.messages.collectAsState()
        val currentStory = viewModel.currentStory

        var voted by remember { mutableStateOf(false) }

        LaunchedEffect(viewModel.currentStory) {
            viewModel.currentStory?.let { Log.d(logTag, it.title) }
            viewModel.fetchVotes()
            voted = false
        }

        // Handle message
        LaunchedEffect(messages.value.lastOrNull()) {
            val message = messages.value.lastOrNull() ?: return@LaunchedEffect

            when (message) {
                is ParticipantAdd -> Log.d(logTag, "participant add")
                is ParticipantRemove -> Log.d(logTag, "participant remove")
                is RevealVotes -> Log.d(logTag, "reveal votes")
                is ResetVotes -> Log.d(logTag, "reset votes")
                is VoteUpdate -> Log.d(logTag, "vote update")
                is AddStory -> Log.d(logTag, "add story")
                is RemoveStory -> Log.d(logTag, "remove story")
                is Summon -> Log.d(logTag, "summon")
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                        participants.value?.let { list ->
                            items(list, key = { it.id }) { participant ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                horizontal = 24.dp,
                                            ).border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(20.dp))
                                            .padding(24.dp),
                                ) {
                                    Text(
                                        participant.profile.nickname,
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .weight(1f),
                                    )
                                    viewModel.currentStory?.let { story ->
                                        if (story.is_revealed) {
                                            Box(
                                                modifier =
                                                    Modifier
                                                        .wrapContentSize()
                                                        .clip(shape = CircleShape)
                                                        .background(color = MaterialTheme.colorScheme.primary),
                                            ) {
                                                votes.value?.let {
                                                    for (vote in it) {
                                                        if (vote.user.id == participant.id && vote.story_id == viewModel.currentStory?.id) {
                                                            Text(
                                                                vote.value,
                                                                fontSize = 20.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                modifier =
                                                                    Modifier.padding(
                                                                        vertical = 8.dp,
                                                                        horizontal = 14.dp,
                                                                    ),
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            votes.value?.let {
                                                for (vote in it) {
                                                    if (vote.user.id == participant.id && vote.story_id == viewModel.currentStory?.id) {
                                                        voted = true
                                                    }
                                                }
                                            }
                                            if (voted) {
                                                Box(
                                                    modifier =
                                                        Modifier
                                                            .size(20.dp)
                                                            .clip(shape = CircleShape)
                                                            .background(color = MaterialTheme.colorScheme.primary),
                                                )
                                                voted = false
                                            } else {
                                                Box(
                                                    modifier =
                                                        Modifier
                                                            .size(20.dp)
                                                            .clip(shape = CircleShape)
                                                            .background(color = MaterialTheme.colorScheme.tertiary),
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.padding(4.dp))
                            }
                        }
                    }
                }
            }
            StoriesSidebar()
        }
    } else {
        RoomJoinCreateScreen { name, type ->
            viewModel.createRoom(name, type)
        }
    }
}
