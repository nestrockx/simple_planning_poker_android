package com.wegielek.simpleplanningpoker.data.repository

import android.content.Context
import com.wegielek.simpleplanningpoker.data.remote.PokerApiService
import com.wegielek.simpleplanningpoker.domain.models.JoinRoomResponse
import com.wegielek.simpleplanningpoker.domain.models.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.Room
import com.wegielek.simpleplanningpoker.domain.models.RoomResponse
import com.wegielek.simpleplanningpoker.domain.models.auth.GuestLoginRequest
import com.wegielek.simpleplanningpoker.domain.models.auth.LoginRequest
import com.wegielek.simpleplanningpoker.domain.models.auth.RegisterRequest
import com.wegielek.simpleplanningpoker.domain.models.post.CreateRoomRequest
import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository
import com.wegielek.simpleplanningpoker.prefs.Preferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PokerRepositoryImpl
    @Inject
    constructor(
        private val pokerApiService: PokerApiService,
        @ApplicationContext private val context: Context,
    ) : PokerRepository {
        // Auth
        override suspend fun login(
            username: String,
            password: String,
        ): Boolean =
            pokerApiService
                .login(LoginRequest(username, password))
                .also {
                    Preferences.saveTokenToStorage(context, it.accessToken)
                }.accessToken
                .isNotEmpty()

        override suspend fun register(
            username: String,
            nickname: String,
            password: String,
        ): Boolean =
            pokerApiService
                .register(RegisterRequest(username, nickname, password))
                .also {
                    Preferences.saveTokenToStorage(context, it.accessToken)
                }.accessToken
                .isNotEmpty()

        override suspend fun guestLogin(nickname: String): Boolean =
            pokerApiService
                .guestLogin(GuestLoginRequest(nickname))
                .also {
                    Preferences.saveTokenToStorage(context, it.accessToken)
                }.accessToken
                .isNotEmpty()

        override suspend fun logout() = pokerApiService.logout()

        // User
        override suspend fun getUserInfo(): ParticipantUser = pokerApiService.getUserInfo()

        // Room
        override suspend fun getRooms(): RoomResponse = pokerApiService.getRooms()

        override suspend fun getRoom(code: String): Room = pokerApiService.getRoom(code)

        override suspend fun joinRoom(code: String): JoinRoomResponse = pokerApiService.joinRoom(code)

        override suspend fun createRoom(
            name: String,
            type: String,
        ): Room =
            pokerApiService.createRoom(
                CreateRoomRequest(
                    name,
                    type,
                ),
            )
    }
