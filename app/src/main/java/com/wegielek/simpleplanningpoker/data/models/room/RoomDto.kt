package com.wegielek.simpleplanningpoker.data.models.room

import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.room.Room

data class RoomDto(
    val id: Int,
    val name: String,
    val type: String,
    val code: String,
    val participants: List<ParticipantUser>,
    val created_by: ParticipantUser,
    val created_at: String,
) {
    fun toDomain() = Room(id, name, type, code, participants, created_by, created_at)
}
