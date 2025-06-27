package com.wegielek.simpleplanningpoker.domain.models.room

import java.time.OffsetDateTime

data class Story(
    val id: Int,
    val room_id: Int,
    val title: String,
    val is_active: Boolean,
    val is_revealed: Boolean,
    val created_at: OffsetDateTime,
)
