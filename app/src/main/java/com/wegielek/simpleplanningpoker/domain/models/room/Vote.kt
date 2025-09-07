package com.wegielek.simpleplanningpoker.domain.models.room

import com.google.gson.annotations.SerializedName

data class Vote(
    val id: Int,
    @SerializedName("story_id") val storyId: Int,
    val user: ParticipantUser,
    val value: String,
    @SerializedName("voted_at") val votedAt: String,
)
