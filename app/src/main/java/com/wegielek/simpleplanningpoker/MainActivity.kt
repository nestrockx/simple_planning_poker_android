package com.wegielek.simpleplanningpoker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wegielek.simpleplanningpoker.ui.theme.SimplePlanningPokerTheme
import com.wegielek.simpleplanningpoker.viewmodels.AuthViewModel
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
fun LoginForm(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginClick: (String, String) -> Unit,
) {
    val username = viewModel.username
    val password = viewModel.password
    val isPasswordVisible = viewModel.isPasswordVisible

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { viewModel.onUsernameChanged(it) },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation =
                if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            trailingIcon = {
                val image = if (isPasswordVisible) Icons.Default.Close else Icons.Default.Refresh
                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onLoginClick(username, password) },
//            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
        ) {
            Text("Login", modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp))
        }
    }
}

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
) {
    LaunchedEffect(viewModel.loginResult) {
        if (viewModel.loginResult) {
            onNavigate()
        }
    }

    LoginForm { username, password ->
        Log.d("Login", "Username: $username, Password: $password")
        viewModel.login()
    }
}

@Composable
fun CreateJoinRoomScreen(onNavigate: () -> Unit) {
}

@Composable
fun RoomScreen(onNavigate: () -> Unit) {
}

@Composable
fun AccountScreen(onNavigate: () -> Unit) {
//    Button(onClick = onNavigate) {
//        Text("Go to Login")
//    }
}

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(bottomBar = { BottomNavigationBar(navController) }, modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            NavHost(navController = navController, startDestination = "auth", modifier = Modifier.padding(innerPadding)) {
                composable("auth") {
                    AuthScreen {
                        navController.navigate("")
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
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    if (Screen.bottomNavItems.any { it.route == currentRoute }) {
        NavigationBar {
            Screen.bottomNavItems.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(screen.icon, contentDescription = screen.label) },
                    label = { Text(screen.label) },
                    selected = currentRoute == screen.route,
                    onClick = {
                        if (currentRoute != screen.route) {
                            navController.navigate(screen.route) {
                                popUpTo(Screen.CreateJoinRoom.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                )
            }
        }
    }
}

sealed class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    object Auth : Screen("auth", "Auth", Icons.Default.Person)

    object CreateJoinRoom : Screen("create_join_room", "Join/Create", Icons.Default.AddBox)

    object Room : Screen("room", "Room", Icons.Default.MeetingRoom)

    companion object {
        val bottomNavItems = listOf(Auth, CreateJoinRoom, Room)
    }
}
