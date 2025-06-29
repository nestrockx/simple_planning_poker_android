package com.wegielek.simpleplanningpoker.presentation.ui.views.room

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

@Composable
fun RoomScreen(
    viewModel: RoomViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
) {
    val logTag = "RoomScreen"

    val room = viewModel.room.collectAsState()
    val stories = viewModel.stories.collectAsState()
    val votes = viewModel.votes.collectAsState()
    val messages = viewModel.messages.collectAsState()

    LaunchedEffect(viewModel.currentStory) {
        viewModel.currentStory?.let { Log.d(logTag, it.title) }
        viewModel.fetchVotes()
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
            room.value?.id?.let { Text(it.toString()) }
            room.value?.code?.let { Text(it) }
            room.value?.type?.let { Text(it) }
            room.value?.created_at?.let { Text(it) }
            room.value?.created_by?.let { Text("${it.username} ${it.profile.nickname}") }
            room.value
                ?.participants
                ?.size
                ?.let { Text(it.toString()) }

            stories.value
                ?.get(0)
                ?.room_id
                ?.let { Text(it.toString()) }
            stories.value
                ?.get(0)
                ?.title
                ?.let { Text(it) }

            votes.value
                ?.size
                ?.let { Text(it.toString()) }

            Column {
                Text("Messages")
                LazyColumn {
                    items(messages.value) { message ->
                        Text(text = message.type)
                    }
                }
            }
//            votes.value
//                ?.get(0)
//                ?.value
//                ?.let { Text(it.toString()) }
        }
        StoriesSidebar()
    }
}
