package com.wegielek.simpleplanningpoker.domain.models.post

import com.google.gson.annotations.SerializedName

data class CreateVoteResponse(
    @SerializedName("story_id") val storyId: Int,
    val value: String,
)
