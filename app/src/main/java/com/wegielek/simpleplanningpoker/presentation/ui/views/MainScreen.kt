package com.wegielek.simpleplanningpoker.presentation.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wegielek.simpleplanningpoker.CreateJoinRoomScreen
import com.wegielek.simpleplanningpoker.ShareScreen
import com.wegielek.simpleplanningpoker.presentation.ui.views.auth.AuthScreen
import com.wegielek.simpleplanningpoker.presentation.ui.views.bottomnavbar.BottomNavigationBar
import com.wegielek.simpleplanningpoker.presentation.ui.views.room.RoomScreen

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val startDestination = "room"
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        modifier =
            Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            NavHost(navController = navController, startDestination = startDestination) {
                composable("auth") {
                    AuthScreen {
                        navController.navigate("account")
                    }
                }
                composable("create_join_room") {
                    CreateJoinRoomScreen {
                        navController.navigate("")
                    }
                }
                composable("room") {
                    RoomScreen {
                        navController.navigate("")
                    }
                }
                composable("account") {
                    AccountScreen {
                        navController.navigate("auth")
                    }
                }
                composable("share") {
                    ShareScreen {
                        navController.navigate("")
                    }
                }
            }
        }
    }
}
