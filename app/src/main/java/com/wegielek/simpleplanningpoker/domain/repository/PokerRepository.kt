package com.wegielek.simpleplanningpoker.domain.repository

import com.wegielek.simpleplanningpoker.domain.models.room.JoinRoomResponse
import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.room.Room
import com.wegielek.simpleplanningpoker.domain.models.room.RoomResponse

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
