package com.wegielek.simpleplanningpoker.data.models.post

import com.wegielek.simpleplanningpoker.domain.models.post.CreateVoteRequest

data class CreateVoteRequestDto(
    val story_id: Int,
    val value: String,
) {
    fun toDomain() = CreateVoteRequest(story_id, value)
}
