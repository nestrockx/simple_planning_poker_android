package com.wegielek.simpleplanningpoker.data.models.room

import com.google.gson.annotations.SerializedName
import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.room.Room

data class RoomDto(
    val id: Int,
    val name: String,
    val type: String,
    val code: String,
    val participants: List<ParticipantUser>,
    @SerializedName("created_by") val createdBy: ParticipantUser,
    @SerializedName("created_at") val createdAt: String,
) {
    fun toDomain() = Room(id, name, type, code, participants, createdBy, createdAt)
}
