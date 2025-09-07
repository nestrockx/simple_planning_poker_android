package com.wegielek.simpleplanningpoker.data.models.post

import com.google.gson.annotations.SerializedName
import com.wegielek.simpleplanningpoker.domain.models.post.CreateVoteRequest

data class CreateVoteRequestDto(
    @SerializedName("story_id") val storyId: Int,
    val value: String,
) {
    fun toDomain() = CreateVoteRequest(storyId, value)
}
