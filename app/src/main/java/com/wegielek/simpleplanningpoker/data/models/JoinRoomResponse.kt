package com.wegielek.simpleplanningpoker.data.models

data class JoinRoomResponse(
    val message: String,
    val room_code: String,
    val participants: List<ParticipantUser>,
)
