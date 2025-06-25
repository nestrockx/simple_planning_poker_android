package com.wegielek.simpleplanningpoker.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wegielek.simpleplanningpoker.data.repository.PokerRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

sealed class AuthState {
    object Login : AuthState()

    object GuestLogin : AuthState()

    object Register : AuthState()
}

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val repository: PokerRepositoryImpl,
    ) : ViewModel() {
        var loginResult by mutableStateOf(false)
            private set
        var guestLoginResult by mutableStateOf(false)
            private set
        var registerResult by mutableStateOf(false)
            private set
        var username by mutableStateOf("")
            private set
        var nickname by mutableStateOf("")
            private set
        var password by mutableStateOf("")
            private set
        var isPasswordVisible by mutableStateOf(false)
            private set
        var authState: AuthState by mutableStateOf(AuthState.Login)
            private set

        fun onUsernameChanged(newUsername: String) {
            username = newUsername
        }

        fun onNicknameChanged(newNickname: String) {
            nickname = newNickname
        }

        fun onPasswordChanged(newPassword: String) {
            password = newPassword
        }

        fun togglePasswordVisibility() {
            isPasswordVisible = !isPasswordVisible
        }

        fun toggleAuthForm() {
            authState = AuthState.Register
        }

        fun guestLogin() {
            viewModelScope.launch {
                try {
                    Log.d("AuthViewModel", "Logging in with username: $username, password: $password")
                    guestLoginResult = repository.guestLogin(nickname = nickname)
                } catch (e: HttpException) {
                    Log.e("AuthViewModel", "Login failed", e)
                }
            }
        }

        fun login() {
            viewModelScope.launch {
                try {
                    Log.d("AuthViewModel", "Logging in with username: $username, password: $password")
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        loginResult = repository.login(username = username, password = password)
                    }
                } catch (e: HttpException) {
                    Log.e("AuthViewModel", "Login failed", e)
                }
            }
        }

        fun register() {
            viewModelScope.launch {
                try {
                    Log.d("AuthViewModel", "Registering with username: $username, nickname: $nickname, password: $password")
                    registerResult = repository.register(username = username, nickname = nickname, password = password)
                } catch (e: HttpException) {
                    Log.e("AuthViewModel", "Registration failed", e)
                }
            }
        }
    }
