package com.wegielek.simpleplanningpoker.data.models.post

import com.wegielek.simpleplanningpoker.domain.models.post.UpdateProfileRequest

data class UpdateProfileRequestDto(
    val nickname: String,
) {
    fun toDomain() = UpdateProfileRequest(nickname)
}
