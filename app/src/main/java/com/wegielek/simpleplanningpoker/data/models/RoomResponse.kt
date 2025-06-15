package com.wegielek.simpleplanningpoker.data.models

data class RoomResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Room>,
)
