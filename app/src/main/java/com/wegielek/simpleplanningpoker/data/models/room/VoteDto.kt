package com.wegielek.simpleplanningpoker.data.models.room

import com.google.gson.annotations.SerializedName
import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.room.Vote

data class VoteDto(
    val id: Int,
    @SerializedName("story_id") val storyId: Int,
    val user: ParticipantUser,
    val value: String,
    @SerializedName("voted_at") val votedAt: String,
) {
    fun toDomain() = Vote(id, storyId, user, value, votedAt)
}
