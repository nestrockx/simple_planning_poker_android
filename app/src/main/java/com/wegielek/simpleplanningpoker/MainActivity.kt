package com.wegielek.simpleplanningpoker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.wegielek.simpleplanningpoker.presentation.ui.theme.SimplePlanningPokerTheme
import com.wegielek.simpleplanningpoker.presentation.ui.views.MainScreen
import com.wegielek.simpleplanningpoker.presentation.viewmodels.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimplePlanningPokerTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun CreateJoinRoomScreen(onNavigate: () -> Unit) {
    Text("CreateJoinRoom")
}

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
) {
    val user = viewModel.user.collectAsState().value
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        user?.username?.let { Text("Username: $it") }
        user?.profile?.nickname?.let { Text("Nickname: $it") }
    }
}

@Composable
fun ShareScreen(onNavigate: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Share")
    }
}

sealed class Screen(
    val route: String,
    val label: String,
    val iconProvider: @Composable () -> ImageVector,
) {
    object Account : Screen("account", "Account", { Icons.Default.ManageAccounts })

    object CreateJoinRoom : Screen(
        "share",
        "Share",
        { Icons.Default.Share },
    )

    object Room : Screen("room", "Room", { ImageVector.vectorResource(R.drawable.ic_playing_cards) })

    companion object {
        val bottomNavItems = listOf(CreateJoinRoom, Room, Account)
    }
}
