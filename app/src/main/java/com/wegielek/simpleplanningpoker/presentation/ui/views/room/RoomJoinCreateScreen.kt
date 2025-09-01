package com.wegielek.simpleplanningpoker.presentation.ui.views.room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wegielek.simpleplanningpoker.presentation.viewmodels.RoomViewModel
import com.wegielek.simpleplanningpoker.utils.ScreenUtils.Companion.pxToDp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun RoomJoinCreateScreen(
    viewModel: RoomViewModel = hiltViewModel(),
    onCreateRoomClick: (String, String) -> Unit,
    onJoinRoomClick: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var createRoomLoading by remember { mutableStateOf(false) }
    var joinRoomLoading by remember { mutableStateOf(false) }

    val roomName = viewModel.roomNameField
    val roomCode = viewModel.roomCodeField

    val verticalScreenWidth =
        min(
            LocalWindowInfo.current.containerSize.width,
            LocalWindowInfo.current.containerSize.height,
        )
    val horizontalPadding = 24.dp

    fun onCreateRoom(name: String) {
        if (name.isBlank()) return
        createRoomLoading = true
        onCreateRoomClick(name, "default") // TODO add room options
        scope.launch {
            delay(2000)
            createRoomLoading = false
        }
    }

    fun onJoinRoom(code: String) {
        if (code.isBlank()) return
        joinRoomLoading = true
        onJoinRoomClick(code)
        scope.launch {
            delay(2000)
            joinRoomLoading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier =
                Modifier
                    .width(pxToDp(verticalScreenWidth))
                    .padding(horizontalPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Create form
            Text("Create Room", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = roomName,
                onValueChange = { viewModel.onRoomNameChanged(it) },
                label = { Text("Room Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (!createRoomLoading) {
                Button(
                    onClick = { onCreateRoom(roomName) },
                ) {
                    Text("Create", modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp))
                }
            } else {
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 8.dp))
            }
            Spacer(Modifier.padding(20.dp))

            // Join form
            Text("Join Room", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = roomCode,
                onValueChange = { viewModel.onRoomCodeChanged(it) },
                label = { Text("Room Code") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (!joinRoomLoading) {
                Button(
                    onClick = { onJoinRoom(roomCode) },
                ) {
                    Text("Join", modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp))
                }
            } else {
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}
