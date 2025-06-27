package com.wegielek.simpleplanningpoker.data.models.post

import com.wegielek.simpleplanningpoker.domain.models.post.CreateStoryRequest

data class CreateStoryRequestDto(
    val room_id: Int,
    val title: String,
) {
    fun toDomain() = CreateStoryRequest(room_id, title)
}
