package com.wegielek.simpleplanningpoker.domain.models.room

data class ParticipantUser(
    val id: Int,
    val username: String,
    val email: String,
    val profile: Profile,
)
