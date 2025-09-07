package com.wegielek.simpleplanningpoker.domain.models.post

import com.google.gson.annotations.SerializedName

data class CreateStoryRequest(
    @SerializedName("room_id") val roomId: Int,
    val title: String,
)
