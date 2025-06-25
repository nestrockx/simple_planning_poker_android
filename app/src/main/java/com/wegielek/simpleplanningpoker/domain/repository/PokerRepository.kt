package com.wegielek.simpleplanningpoker.domain.repository

import com.wegielek.simpleplanningpoker.domain.models.JoinRoomResponse
import com.wegielek.simpleplanningpoker.domain.models.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.Room
import com.wegielek.simpleplanningpoker.domain.models.RoomResponse

interface PokerRepository {
    suspend fun login(
        username: String,
        password: String,
    ): Boolean

    suspend fun register(
        username: String,
        nickname: String,
        password: String,
    ): Boolean

    suspend fun guestLogin(nickname: String): Boolean

    suspend fun logout()

    suspend fun getUserInfo(): ParticipantUser

    suspend fun getRooms(): RoomResponse

    suspend fun getRoom(code: String): Room

    suspend fun joinRoom(code: String): JoinRoomResponse

    suspend fun createRoom(
        name: String,
        type: String,
    ): Room
}
