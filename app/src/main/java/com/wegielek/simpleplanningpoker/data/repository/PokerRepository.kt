package com.wegielek.simpleplanningpoker.data.repository

import android.content.Context
import com.wegielek.simpleplanningpoker.data.api.PokerApiService
import com.wegielek.simpleplanningpoker.data.models.ParticipantUser
import com.wegielek.simpleplanningpoker.data.models.RoomResponse
import com.wegielek.simpleplanningpoker.data.models.auth.GuestLoginRequest
import com.wegielek.simpleplanningpoker.data.models.auth.LoginRequest
import com.wegielek.simpleplanningpoker.data.models.auth.RegisterRequest
import com.wegielek.simpleplanningpoker.data.models.post.CreateRoomRequest
import com.wegielek.simpleplanningpoker.prefs.Preferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PokerRepository
    @Inject
    constructor(
        private val pokerApiService: PokerApiService,
        @ApplicationContext private val context: Context,
    ) {
        // Auth
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

        // User
        suspend fun getUserInfo(): ParticipantUser = pokerApiService.getUserInfo()

        // Room
        suspend fun getRooms(): RoomResponse = pokerApiService.getRooms()

        suspend fun getRoom(code: String) = pokerApiService.getRoom(code)

        suspend fun joinRoom(code: String) = pokerApiService.joinRoom(code)

        suspend fun createRoom(
            name: String,
            type: String,
        ) = pokerApiService.createRoom(
            CreateRoomRequest(
                name,
                type,
            ),
        )
    }
