package com.wegielek.simpleplanningpoker.data.models.post

import com.wegielek.simpleplanningpoker.domain.models.post.CreateVoteRequest
import com.wegielek.simpleplanningpoker.domain.models.post.CreateVoteResponse

data class CreateVoteResponseDto(
    val story_id: Int,
    val value: String,
) {
    fun toDomain() = CreateVoteResponse(story_id, value)
}
