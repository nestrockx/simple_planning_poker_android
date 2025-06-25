package com.wegielek.simpleplanningpoker.presentation.ui.views.auth

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wegielek.simpleplanningpoker.presentation.viewmodels.AuthState
import com.wegielek.simpleplanningpoker.presentation.viewmodels.AuthViewModel

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

    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        Column(modifier = Modifier.align(Alignment.Center).verticalScroll(rememberScrollState())) {
            Button(
                modifier = Modifier.padding(2.dp),
                onClick = { viewModel.toggleAuthForm() },
            ) { Text("Change") }
            when (viewModel.authState) {
                is AuthState.Login -> {
                    LoginForm { username, password ->
                        Log.d("Login", "Username: $username, Password: $password")
                        viewModel.login()
                    }
                }
                is AuthState.Register -> {
                    RegistrationForm { username, nickname, password ->
                        Log.d("Registration", "Username: $username, Nickname: $nickname, Password: $password")
                        viewModel.register()
                    }
                }
                is AuthState.GuestLogin -> {
                    GuestLoginForm { nickname ->
                        Log.d("Guest login", "Nickname: $nickname")
                        viewModel.guestLogin()
                    }
                }
            }
        }
    }
}
