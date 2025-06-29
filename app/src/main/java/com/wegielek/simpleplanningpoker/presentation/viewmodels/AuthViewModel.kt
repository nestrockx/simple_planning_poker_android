package com.wegielek.simpleplanningpoker.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wegielek.simpleplanningpoker.domain.usecases.auth.GuestLoginUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.auth.LoginUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.auth.RegisterUseCase
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
        private val loginUseCase: LoginUseCase,
        private val registerUseCase: RegisterUseCase,
        private val guestLoginUseCase: GuestLoginUseCase,
    ) : ViewModel() {
        companion object {
            private const val LOG_TAG = "AuthViewModel"
        }

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

        fun setAuthForm(authState: AuthState) {
            this.authState = authState
        }

        fun clearLoginResult() {
            loginResult = false
        }

        fun clearGuestLoginResult() {
            guestLoginResult = false
        }

        fun clearRegisterResult() {
            registerResult = false
        }

        fun login() {
            viewModelScope.launch {
                try {
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        Log.d(LOG_TAG, "Logging in with username: $username")
                        loginResult = loginUseCase(username, password)
                    }
                } catch (e: HttpException) {
                    Log.e(LOG_TAG, "Login failed", e)
                }
            }
        }

        fun register() {
            viewModelScope.launch {
                try {
                    if (username.isNotEmpty() && nickname.isNotEmpty() && password.isNotEmpty()) {
                        Log.d(
                            LOG_TAG,
                            "Registering with username: $username, nickname: $nickname",
                        )
                        registerResult = registerUseCase(username, nickname, password)
                    }
                } catch (e: HttpException) {
                    Log.e(LOG_TAG, "Registration failed", e)
                }
            }
        }

        fun guestLogin() {
            viewModelScope.launch {
                try {
                    Log.d(LOG_TAG, "Logging in with nickname: $nickname")
                    guestLoginResult = guestLoginUseCase(nickname)
                } catch (e: HttpException) {
                    Log.e(LOG_TAG, "Guest login failed", e)
                }
            }
        }
    }
