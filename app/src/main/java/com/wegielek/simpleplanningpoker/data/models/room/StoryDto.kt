package com.wegielek.simpleplanningpoker.data.models.room

import com.google.gson.annotations.SerializedName
import com.wegielek.simpleplanningpoker.domain.models.room.Story

data class StoryDto(
    val id: Int,
    @SerializedName("room_id") val roomId: Int,
    val title: String,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("is_revealed") val isRevealed: Boolean,
    @SerializedName("created_at") val createdAt: String,
) {
    fun toDomain() = Story(id, roomId, title, isActive, isRevealed, createdAt)
}
