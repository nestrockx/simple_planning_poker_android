package com.wegielek.simpleplanningpoker.domain.models.room

import com.google.gson.annotations.SerializedName

data class Room(
    val id: Int,
    val name: String,
    val type: String,
    val code: String,
    val participants: List<ParticipantUser>,
    @SerializedName("created_by") val createdBy: ParticipantUser,
    @SerializedName("created_at") val createdAt: String,
)
