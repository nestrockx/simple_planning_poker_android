package com.wegielek.simpleplanningpoker.data.models.post

import com.google.gson.annotations.SerializedName
import com.wegielek.simpleplanningpoker.domain.models.post.CreateStoryRequest

data class CreateStoryRequestDto(
    @SerializedName("room_id") val roomId: Int,
    val title: String,
) {
    fun toDomain() = CreateStoryRequest(roomId, title)
}
