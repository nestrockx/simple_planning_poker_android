package com.wegielek.simpleplanningpoker.data.models.room

import com.wegielek.simpleplanningpoker.domain.models.room.Story
import java.time.OffsetDateTime

data class StoryDto(
    val id: Int,
    val room_id: Int,
    val title: String,
    val is_active: Boolean,
    val is_revealed: Boolean,
    val created_at: OffsetDateTime,
) {
    fun toDomain() = Story(id, room_id, title, is_active, is_revealed, created_at)
}
