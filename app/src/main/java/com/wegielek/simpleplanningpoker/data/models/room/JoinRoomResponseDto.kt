package com.wegielek.simpleplanningpoker.data.models.room

import com.google.gson.annotations.SerializedName
import com.wegielek.simpleplanningpoker.domain.models.room.JoinRoomResponse

data class JoinRoomResponseDto(
    val message: String,
    @SerializedName("room_code") val roomCode: String,
    val participants: List<String>,
) {
    fun toDomain() = JoinRoomResponse(message, roomCode, participants)
}
