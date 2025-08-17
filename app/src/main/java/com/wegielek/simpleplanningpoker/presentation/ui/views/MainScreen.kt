package com.wegielek.simpleplanningpoker.presentation.ui.views

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wegielek.simpleplanningpoker.prefs.Preferences
import com.wegielek.simpleplanningpoker.presentation.ui.views.auth.AuthScreen
import com.wegielek.simpleplanningpoker.presentation.ui.views.bottomnavbar.BottomNavigationBar
import com.wegielek.simpleplanningpoker.presentation.ui.views.room.RoomScreen
import com.wegielek.simpleplanningpoker.presentation.ui.views.room.ShareScreen

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val context: Context = LocalContext.current
//    val startDestination = if (Preferences.getTokenFromStorage(context) != null) "room" else "auth"
    val startDestination = "splash"

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
                composable("splash") {
                    LaunchedEffect(Unit) {
                        val token = Preferences.getTokenOnce(context)
                        navController.navigate(if (token != null) "room" else "auth") {
                            popUpTo("splash") { inclusive = true }
                        }
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
