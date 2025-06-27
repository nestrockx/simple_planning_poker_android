package com.wegielek.simpleplanningpoker.data.repository

import android.content.Context
import com.wegielek.simpleplanningpoker.data.models.auth.GuestLoginRequestDto
import com.wegielek.simpleplanningpoker.data.models.auth.LoginRequestDto
import com.wegielek.simpleplanningpoker.data.models.auth.RegisterRequestDto
import com.wegielek.simpleplanningpoker.data.models.post.CreateRoomRequestDto
import com.wegielek.simpleplanningpoker.data.remote.PokerApiService
import com.wegielek.simpleplanningpoker.domain.models.room.JoinRoomResponse
import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.room.Room
import com.wegielek.simpleplanningpoker.domain.models.room.RoomResponse
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
                .login(LoginRequestDto(username, password))
                .toDomain()
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
                .register(RegisterRequestDto(username, nickname, password))
                .toDomain()
                .also {
                    Preferences.saveTokenToStorage(context, it.accessToken)
                }.accessToken
                .isNotEmpty()

        override suspend fun guestLogin(nickname: String): Boolean =
            pokerApiService
                .guestLogin(GuestLoginRequestDto(nickname))
                .toDomain()
                .also {
                    Preferences.saveTokenToStorage(context, it.accessToken)
                }.accessToken
                .isNotEmpty()

        override suspend fun logout() = pokerApiService.logout()

        // User
        override suspend fun getUserInfo(): ParticipantUser = pokerApiService.getUserInfo().toDomain()

        // Room
        override suspend fun getRooms(): RoomResponse = pokerApiService.getRooms().toDomain()

        override suspend fun getRoom(code: String): Room = pokerApiService.getRoom(code).toDomain()

        override suspend fun joinRoom(code: String): JoinRoomResponse = pokerApiService.joinRoom(code).toDomain()

        override suspend fun createRoom(
            name: String,
            type: String,
        ): Room =
            pokerApiService
                .createRoom(
                    CreateRoomRequestDto(
                        name,
                        type,
                    ),
                ).toDomain()
    }
