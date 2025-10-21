package com.wegielek.simpleplanningpoker.presentation.ui.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wegielek.simpleplanningpoker.R
import com.wegielek.simpleplanningpoker.presentation.ui.views.auth.AuthScreen
import com.wegielek.simpleplanningpoker.presentation.ui.views.bottomnavbar.BottomNavigationBar
import com.wegielek.simpleplanningpoker.presentation.ui.views.room.RoomScreen
import com.wegielek.simpleplanningpoker.presentation.ui.views.room.ShareScreen
import com.wegielek.simpleplanningpoker.presentation.viewmodels.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val startDestination = "splash"

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        modifier =
            Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            NavHost(navController = navController, startDestination = startDestination) {
                composable("auth") {
                    AuthScreen {
                        navController.navigate("account")
                    }
                }
                composable("splash") {
                    LaunchedEffect(Unit) {
                        try {
                            viewModel.getUserInfo().await()
                            navController.navigate("room") {
                                popUpTo("splash") { inclusive = true }
                            }
                        } catch (e: Exception) {
                            Log.e("MainScreen", "Error getting user info", e)
                            navController.navigate("auth") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    }
                }
                composable("room") {
                    RoomScreen()
                }
                composable("account") {
                    AccountScreen {
                        navController.navigate("auth")
                    }
                }
                composable("share") {
                    ShareScreen {
                        navController.navigate("room") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    }
}
