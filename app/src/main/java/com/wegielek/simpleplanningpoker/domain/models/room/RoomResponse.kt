package com.wegielek.simpleplanningpoker.domain.models.room

data class RoomResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Room>,
)
