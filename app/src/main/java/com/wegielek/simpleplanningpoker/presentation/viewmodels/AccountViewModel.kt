package com.wegielek.simpleplanningpoker.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.usecases.auth.LogoutUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.user.GetUserInfoUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.user.UpdateNicknameUseCase
import com.wegielek.simpleplanningpoker.prefs.Preferences.clearRoomCodeFromStorage
import com.wegielek.simpleplanningpoker.prefs.Preferences.clearToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel
    @Inject
    constructor(
        private val updateNicknameUseCase: UpdateNicknameUseCase,
        private val getUserInfoUseCase: GetUserInfoUseCase,
        private val logoutUseCase: LogoutUseCase,
    ) : ViewModel() {
        val logTag = "AccountViewModel"

        var isLoggedOut by mutableStateOf(false)
            private set

        var isNicknameInEdit by mutableStateOf(false)
            private set

        var nicknameField by mutableStateOf("")
            private set

        private val _user = MutableStateFlow<ParticipantUser?>(null)
        val user: StateFlow<ParticipantUser?> = _user

        init {
            fetchUserInfo()
        }

        private fun fetchUserInfo() {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    Log.d(logTag, "Fetching user data")
                    _user.value = getUserInfoUseCase()
                    nicknameField = _user.value?.profile?.nickname ?: ""
                } catch (e: Exception) {
                    Log.e(logTag, "Error fetching user data", e)
                }
            }
        }

        fun logout(context: Context) {
            viewModelScope.launch(Dispatchers.IO) {
                clearRoomCodeFromStorage(context)
                clearToken(context)
                isLoggedOut = true
                try {
                    logoutUseCase()
                } catch (e: Exception) {
                    Log.e(logTag, "Error logging out", e)
                }
            }
        }

        fun clearLogout() {
            isLoggedOut = false
        }

        fun onNicknameChanged(nickname: String) {
            nicknameField = nickname
        }

        fun editNickname(edit: Boolean) {
            isNicknameInEdit = edit
        }

        fun updateNickname() {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (nicknameField.isBlank()) {
                        throw Exception("Nickname cannot be empty")
                    }
                    updateNicknameUseCase(nicknameField.trim())
                    fetchUserInfo()
                } catch (e: Exception) {
                    Log.e(logTag, "Error updating nickname", e)
                }
            }
        }
    }
