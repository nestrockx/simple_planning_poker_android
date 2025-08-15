package com.wegielek.simpleplanningpoker.data.models.room

import com.wegielek.simpleplanningpoker.domain.models.room.JoinRoomResponse
import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser

data class JoinRoomResponseDto(
    val message: String,
    val room_code: String,
    val participants: List<String>,
) {
    fun toDomain() = JoinRoomResponse(message, room_code, participants)
}
