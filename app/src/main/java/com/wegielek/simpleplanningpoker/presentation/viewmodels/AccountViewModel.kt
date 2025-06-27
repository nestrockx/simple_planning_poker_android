package com.wegielek.simpleplanningpoker.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wegielek.simpleplanningpoker.data.repository.PokerRepositoryImpl
import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel
    @Inject
    constructor(
        private val repository: PokerRepositoryImpl,
    ) : ViewModel() {
        private val _user = MutableStateFlow<ParticipantUser?>(null)
        val user: StateFlow<ParticipantUser?> = _user

        init {
            fetchUserInfo()
        }

        private fun fetchUserInfo() {
            viewModelScope.launch {
                _user.value = repository.getUserInfo()
                Log.d(
                    "Rooms: ",
                    repository
                        .getRooms()
                        .results.size
                        .toString(),
                )
            }
        }
    }
