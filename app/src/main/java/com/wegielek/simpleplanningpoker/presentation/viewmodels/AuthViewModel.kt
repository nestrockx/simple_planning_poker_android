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
import kotlinx.coroutines.Dispatchers
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
        val logTag = "AuthViewModel"

        var loginResult by mutableStateOf(false)
            private set
        var guestLoginResult by mutableStateOf(false)
            private set
        var registerResult by mutableStateOf(false)
            private set

        var username by mutableStateOf("")
            private set
        var usernameError by mutableStateOf<String?>(null)
            private set

        var nickname by mutableStateOf("")
            private set
        var nicknameError by mutableStateOf<String?>(null)
            private set

        var password by mutableStateOf("")
            private set
        var passwordError by mutableStateOf<String?>(null)
            private set
        var isPasswordVisible by mutableStateOf(false)
            private set

        var authState: AuthState by mutableStateOf(AuthState.Login)
            private set

        fun onUsernameChanged(newUsername: String) {
            username = newUsername
            usernameError = validateUsername(newUsername)
        }

        private fun validateUsername(username: String): String? =
            when {
                username.length < 3 -> "Username must be at least 3 characters"
                username.length > 20 -> "Username cannot be longer than 20 characters"
                else -> null
            }

        fun onNicknameChanged(newNickname: String) {
            nickname = newNickname
            nicknameError = validateNickname(newNickname)
        }

        private fun validateNickname(nickname: String): String? =
            when {
                nickname.length < 3 -> "Display name must be at least 3 characters"
                nickname.length > 20 -> "Display name cannot be longer than 20 characters"
                else -> null
            }

        fun onPasswordChanged(newPassword: String) {
            password = newPassword
            passwordError = validatePassword(newPassword)
        }

        private fun validatePassword(password: String): String? =
            when {
                password.length < 7 -> "Password must be at least 7 characters"
                password.length > 20 -> "Password cannot be longer than 20 characters"
                else -> null
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
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (nicknameError == null && passwordError == null) {
                        Log.d(logTag, "Logging in with username: $username")
                        loginResult =
                            loginUseCase(
                                username.trim(),
                                password.trim(),
                            )
                    }
                } catch (e: Exception) {
                    Log.e(logTag, "Login failed", e)
                }
            }
        }

        fun register() {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (nicknameError == null && nicknameError == null && passwordError == null) {
                        Log.d(
                            logTag,
                            "Registering with username: $username, nickname: $nickname",
                        )
                        registerResult =
                            registerUseCase(
                                username.trim(),
                                nickname.trim(),
                                password.trim(),
                            )
                    }
                } catch (e: Exception) {
                    Log.e(logTag, "Registration failed", e)
                }
            }
        }

        fun guestLogin() {
            if (!nickname.isNotEmpty()) return
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (nicknameError == null) {
                        Log.d(logTag, "Logging : $nickname")
                        guestLoginResult = guestLoginUseCase(nickname.trim())
                    }
                } catch (e: Exception) {
                    Log.e(logTag, "Guest login failed", e)
                }
            }
        }
    }
