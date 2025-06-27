package com.wegielek.simpleplanningpoker.data.models.room

import com.wegielek.simpleplanningpoker.domain.models.room.Room
import com.wegielek.simpleplanningpoker.domain.models.room.RoomResponse

data class RoomResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Room>,
) {
    fun toDomain() = RoomResponse(count, next, previous, results)
}
