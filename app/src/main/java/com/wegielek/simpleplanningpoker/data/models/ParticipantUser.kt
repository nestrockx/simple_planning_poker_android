package com.wegielek.simpleplanningpoker.data.models

data class ParticipantUser (
    val id: Int,
    val username: String,
    val email: String,
    val profile: Profile
)