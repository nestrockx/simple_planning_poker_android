package com.wegielek.simpleplanningpoker.data.repository

import android.content.Context
import com.wegielek.simpleplanningpoker.data.api.PokerApiService
import com.wegielek.simpleplanningpoker.data.models.auth.GuestLoginRequest
import com.wegielek.simpleplanningpoker.data.models.auth.LoginRequest
import com.wegielek.simpleplanningpoker.data.models.auth.RegisterRequest
import com.wegielek.simpleplanningpoker.prefs.Preferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PokerRepository
    @Inject
    constructor(
        private val pokerApiService: PokerApiService,
        @ApplicationContext private val context: Context,
    ) {
        suspend fun login(
            username: String,
            password: String,
        ): Boolean =
            pokerApiService
                .login(LoginRequest(username, password))
                .also {
                    Preferences.saveTokenToStorage(context, it.accessToken)
                }.accessToken
                .isNotEmpty()

        suspend fun register(
            username: String,
            password: String,
        ) = pokerApiService.register(RegisterRequest(username, password))

        suspend fun guestLogin(nickname: String) = pokerApiService.guestLogin(GuestLoginRequest(nickname))

        suspend fun logout() = pokerApiService.logout()

        suspend fun getUserInfo() = pokerApiService.getUserInfo()

        suspend fun getRooms() = pokerApiService.getRooms()
    }
