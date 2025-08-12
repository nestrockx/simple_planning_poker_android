package com.wegielek.simpleplanningpoker.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.usecases.GetUserInfoUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel
    @Inject
    constructor(
        private val getUserInfoUseCase: GetUserInfoUseCase,
        private val logoutUseCase: LogoutUseCase,
    ) : ViewModel() {
        companion object {
            private const val LOG_TAG = "AccountViewModel"
        }

        var isLoggedOut by mutableStateOf(false)
            private set

        private val _user = MutableStateFlow<ParticipantUser?>(null)
        val user: StateFlow<ParticipantUser?> = _user

        init {
            fetchUserInfo()
        }

        private fun fetchUserInfo() {
            viewModelScope.launch {
                Log.d(LOG_TAG, "Fetching user data")
                _user.value = getUserInfoUseCase()
//                Log.d(
//                    "Rooms: ",
//                    repository
//                        .getRooms()
//                        .results.size
//                        .toString(),
//                )
            }
        }

        fun logout() {
            viewModelScope.launch {
                logoutUseCase()
                isLoggedOut = true
            }
        }

        fun clearLogout() {
            isLoggedOut = false
        }
    }
