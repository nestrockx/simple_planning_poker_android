package com.wegielek.simpleplanningpoker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wegielek.simpleplanningpoker.data.models.ParticipantUser
import com.wegielek.simpleplanningpoker.data.repository.PokerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val repository: PokerRepository,
    ) : ViewModel() {
        private val _user = MutableStateFlow<ParticipantUser?>(null)
        val user: StateFlow<ParticipantUser?> = _user

        init {
            fetchUserInfo()
        }

        private fun fetchUserInfo() {
            viewModelScope.launch {
                _user.value = repository.getUserInfo()
            }
        }
    }
