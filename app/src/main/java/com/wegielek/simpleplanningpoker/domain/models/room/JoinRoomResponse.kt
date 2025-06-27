package com.wegielek.simpleplanningpoker.domain.models.room

data class JoinRoomResponse(
    val message: String,
    val room_code: String,
    val participants: List<ParticipantUser>,
)
