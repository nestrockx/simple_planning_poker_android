package com.wegielek.simpleplanningpoker.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wegielek.simpleplanningpoker.domain.usecases.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val getUserInfoUseCase: GetUserInfoUseCase,
    ) : ViewModel() {
        fun getUserInfo() =
            viewModelScope.async(Dispatchers.IO) {
                getUserInfoUseCase()
            }
    }
