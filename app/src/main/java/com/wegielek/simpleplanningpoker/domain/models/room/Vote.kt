package com.wegielek.simpleplanningpoker.domain.models.room

data class Vote(
    val id: Int,
    val story_id: Int,
    val user: ParticipantUser,
    val value: String,
    val voted_at: String,
)
