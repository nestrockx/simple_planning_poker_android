package com.wegielek.simpleplanningpoker.data.models.post

import com.wegielek.simpleplanningpoker.domain.models.post.CreateRoomRequest

data class CreateRoomRequestDto(
    val name: String,
    val type: String,
) {
    fun toDomain() = CreateRoomRequest(name, type)
}
