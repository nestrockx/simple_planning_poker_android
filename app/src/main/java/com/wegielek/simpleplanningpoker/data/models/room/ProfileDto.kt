package com.wegielek.simpleplanningpoker.data.models.room

import com.wegielek.simpleplanningpoker.domain.models.room.Profile

data class ProfileDto(
    val nickname: String,
    val moderator: String,
) {
    fun toDomain() = Profile(nickname, moderator)
}
