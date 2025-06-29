package com.wegielek.simpleplanningpoker.domain.models.room

data class Story(
    val id: Int,
    val room_id: Int,
    val title: String,
    val is_active: Boolean,
    val is_revealed: Boolean,
    val created_at: String,
)
