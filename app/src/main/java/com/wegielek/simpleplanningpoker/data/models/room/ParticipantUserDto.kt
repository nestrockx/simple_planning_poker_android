package com.wegielek.simpleplanningpoker.data.models.room

import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.room.Profile

data class ParticipantUserDto(
    val id: Int,
    val username: String,
    val email: String,
    val profile: Profile,
) {
    fun toDomain() = ParticipantUser(id, username, email, profile)
}
