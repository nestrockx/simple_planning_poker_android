package com.wegielek.simpleplanningpoker.domain.models.room

import com.google.gson.annotations.SerializedName

data class JoinRoomResponse(
    val message: String,
    @SerializedName("room_code") val roomCode: String,
    val participants: List<String>,
)
