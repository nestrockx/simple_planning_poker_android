package com.wegielek.simpleplanningpoker.ui.views.auth

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.wegielek.simpleplanningpoker.viewmodels.AuthViewModel

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
