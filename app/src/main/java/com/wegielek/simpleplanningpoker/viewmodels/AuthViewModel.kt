package com.wegielek.simpleplanningpoker.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wegielek.simpleplanningpoker.data.repository.PokerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val repository: PokerRepository,
    ) : ViewModel() {
        var loginResult by mutableStateOf(false)
            private set
        var username by mutableStateOf("")
            private set

        var password by mutableStateOf("")
            private set

        var isPasswordVisible by mutableStateOf(false)
            private set

        fun onUsernameChanged(newUsername: String) {
            username = newUsername
        }

        fun onPasswordChanged(newPassword: String) {
            password = newPassword
        }

        fun togglePasswordVisibility() {
            isPasswordVisible = !isPasswordVisible
        }

        fun login() {
            viewModelScope.launch {
                try {
                    Log.d("AuthViewModel", "Logging in with username: $username, password: $password")
                    loginResult = repository.login(username = username, password = password)
                } catch (e: HttpException) {
                    Log.e("AuthViewModel", "Login failed", e)
                }
            }
        }
    }
