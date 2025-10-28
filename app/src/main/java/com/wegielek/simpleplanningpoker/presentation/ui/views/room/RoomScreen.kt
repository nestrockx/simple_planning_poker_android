package com.wegielek.simpleplanningpoker.presentation.ui.views.room

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.wegielek.simpleplanningpoker.domain.models.websocket.AddStory
import com.wegielek.simpleplanningpoker.domain.models.websocket.ParticipantAdd
import com.wegielek.simpleplanningpoker.domain.models.websocket.ParticipantRemove
import com.wegielek.simpleplanningpoker.domain.models.websocket.RemoveStory
import com.wegielek.simpleplanningpoker.domain.models.websocket.ResetVotes
import com.wegielek.simpleplanningpoker.domain.models.websocket.RevealVotes
import com.wegielek.simpleplanningpoker.domain.models.websocket.Summon
import com.wegielek.simpleplanningpoker.domain.models.websocket.VoteUpdate
import com.wegielek.simpleplanningpoker.presentation.viewmodels.RoomViewModel
import com.wegielek.simpleplanningpoker.utils.ScreenUtils.pxToDp
import kotlin.math.min

@Composable
fun RoomScreen(viewModel: RoomViewModel = hiltViewModel()) {
    val logTag = "RoomScreen"

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val verticalScreenWidth =
        min(
            LocalWindowInfo.current.containerSize.width,
            LocalWindowInfo.current.containerSize.height,
        )
    val horizontalPadding = 24.dp

    DisposableEffect(lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    if (!viewModel.isConnected) {
                        viewModel.connectWebSocket(context)
                    }
                }
            }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(viewModel.roomCode) {
        Log.d(logTag, "Room code: ${viewModel.roomCode}")
        viewModel.getRoomCode(context)
        if (viewModel.roomCode.isNotEmpty()) {
            viewModel.fetchRoom()
            viewModel.connectWebSocket(context)
        } else {
            viewModel.disconnectWebsocket()
        }
    }

    LaunchedEffect(viewModel.isConnected) {
        if (viewModel.isConnected) {
            viewModel.joinRoom()
            try {
                val participant = viewModel.getUserInfo().await()
                Log.d(logTag, "New participant: $participant")
                viewModel.addParticipant(participant)
            } catch (e: Exception) {
                Log.e(logTag, "Error adding participant: ${e.message}")
            }
        }
    }

    LaunchedEffect(viewModel.selectedVoteValue) {
        Log.d(logTag, "Selected vote value: ${viewModel.selectedVoteValue}")
        viewModel.updateUserVote()
    }

    if (viewModel.roomCode.isNotEmpty()) {
        val room by viewModel.room.collectAsState()
        val votes by viewModel.votes.collectAsState()
        val participants by viewModel.participants.collectAsState()
        val messages by viewModel.messages.collectAsState()
        val currentStory = viewModel.currentStory

        LaunchedEffect(viewModel.currentStory) {
            viewModel.currentStory?.let { Log.d(logTag, it.title) }
            viewModel.fetchVotes()
        }

        LaunchedEffect(messages.lastOrNull()) {
            val message = messages.lastOrNull() ?: return@LaunchedEffect
            Log.d(logTag, "Message: $message")

            when (message) {
                is ParticipantAdd -> {
                    Log.d(logTag, "participant add")
                    try {
                        val participant = viewModel.getUser(message.participants.id).await()
                        viewModel.addParticipant(participant)
                    } catch (e: Exception) {
                        Log.e(logTag, "Error adding participant: ${e.message}")
                    }
                }
                is ParticipantRemove -> {
                    Log.d(logTag, "participant remove")
                    viewModel.removeParticipant(message.participants.id)
                }
                is RevealVotes -> {
                    Log.d(logTag, "reveal votes")
                    if (currentStory?.id == message.reveal.storyId) {
                        viewModel.revealVotes(message.reveal.value)
                    }
                }
                is ResetVotes -> {
                    Log.d(logTag, "reset votes")
                    if (currentStory?.id == message.reset.storyId) {
                        viewModel.clearVotes()
                        viewModel.revealVotes(false)
                    }
                }
                is VoteUpdate -> {
                    Log.d(logTag, "vote update")
                    if (currentStory?.id == message.vote.storyId) {
                        viewModel.updateVote(message.vote)
                    }
                }
                is AddStory -> {
                    Log.d(logTag, "add story")
                    viewModel.addStory(message.story)
                }
                is RemoveStory -> {
                    Log.d(logTag, "remove story")
                    viewModel.removeStory(message.story)
                }
                is Summon -> {
                    Log.d(logTag, "summon")
                    viewModel.summon(message.story)
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (viewModel.votingDialogVisible) {
                VotingDialog(
                    onDismiss = { viewModel.hideVotingDialog() },
                    onValueSelected = { viewModel.setVoteValue(it) },
                    votingType = RoomType.valueOf(room?.type?.uppercase() ?: "DEFAULT"),
                )
            }

            StoriesSidebar {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize().padding(top = 70.dp),
                ) {
                    participants?.let { list ->
                        items(list) { participant ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier =
                                    Modifier
                                        .width(pxToDp(verticalScreenWidth))
                                        .padding(
                                            horizontal = horizontalPadding,
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
                                    if (story.isRevealed) {
                                        votes?.let {
                                            for (vote in it) {
                                                if (vote.user.id == participant.id && vote.storyId == viewModel.currentStory?.id) {
                                                    Box(
                                                        modifier =
                                                            Modifier
                                                                .wrapContentSize()
                                                                .clip(shape = CircleShape)
                                                                .background(color = MaterialTheme.colorScheme.primary),
                                                    ) {
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
                                        val voted =
                                            votes?.any { vote ->
                                                vote.user.id == participant.id &&
                                                    vote.storyId == viewModel.currentStory?.id &&
                                                    vote.value.isNotEmpty()
                                            } == true
                                        if (voted) {
                                            Box(
                                                modifier =
                                                    Modifier
                                                        .size(20.dp)
                                                        .clip(shape = CircleShape)
                                                        .background(color = MaterialTheme.colorScheme.primary),
                                            )
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

                val base = MaterialTheme.colorScheme.primary.toArgb()
                val background = MaterialTheme.colorScheme.background.toArgb()
                val blended = ColorUtils.blendARGB(base, background, 0.3f)
                val adjustedColor = Color(blended)
                viewModel.currentStory?.let {
                    if (!it.isRevealed) {
                        FloatingActionButton(
                            onClick = { viewModel.showVotingDialog() },
                            containerColor = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                        ) {
                            Icon(Icons.Default.HowToVote, contentDescription = "Vote")
                        }

                        ExtendedFloatingActionButton(
                            onClick = { viewModel.revealVotesSend(true) },
                            containerColor = adjustedColor,
                            modifier =
                                Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp),
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.RemoveRedEye,
                                    contentDescription = "Reveal votes",
                                )
                            },
                            text = { Text("Reveal") },
                        )
                    } else {
                        ExtendedFloatingActionButton(
                            onClick = { viewModel.revealVotesSend(false) },
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            modifier =
                                Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp),
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.RemoveRedEye,
                                    contentDescription = "Hide votes",
                                )
                            },
                            text = { Text("Hide") },
                        )
                        FloatingActionButton(
                            onClick = { viewModel.resetVotes() },
                            containerColor = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp),
                        ) {
                            Icon(Icons.Default.Cancel, contentDescription = "Reset Votes")
                        }
                    }
                }
            }
        }
    } else {
        RoomJoinCreateScreen(
            onCreateRoomClick = { name, type ->
                viewModel.createRoom(name, type)
            },
            onJoinRoomClick = { code ->
                if (code.length == 6) {
                    viewModel.joinRoom(code) { Log.e(logTag, "Invalid room code: $code") }
                } else {
                    Log.e(logTag, "Invalid room code: $code")
                }
            },
        )
    }
}
