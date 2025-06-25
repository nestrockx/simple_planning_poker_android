package com.wegielek.simpleplanningpoker.domain.models

data class Room(
    val id: Int,
    val name: String,
    val type: String,
    val code: String,
    val participants: List<ParticipantUser>,
    val created_by: ParticipantUser,
    val created_at: String,
)
