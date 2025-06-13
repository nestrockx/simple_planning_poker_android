package com.wegielek.simpleplanningpoker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.wegielek.simpleplanningpoker.ui.theme.SimplePlanningPokerTheme
import com.wegielek.simpleplanningpoker.ui.views.MainScreen
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
fun AccountScreen(onNavigate: () -> Unit) {
    Text("Account")
}

@Composable
fun ShareScreen(onNavigate: () -> Unit) {
    Text("Share")
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
