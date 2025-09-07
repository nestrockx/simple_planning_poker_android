package com.wegielek.simpleplanningpoker.domain.models.room

import com.google.gson.annotations.SerializedName

data class Story(
    val id: Int,
    @SerializedName("room_id") val roomId: Int,
    val title: String,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("is_revealed") val isRevealed: Boolean,
    @SerializedName("created_at") val createdAt: String,
)
