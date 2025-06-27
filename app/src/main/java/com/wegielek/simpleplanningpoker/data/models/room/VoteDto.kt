package com.wegielek.simpleplanningpoker.data.models.room

import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.room.Vote
import java.time.OffsetDateTime

data class VoteDto(
    val id: Int,
    val story_id: Int,
    val user: ParticipantUser,
    val value: String,
    val voted_at: OffsetDateTime,
) {
    fun toDomain() = Vote(id, story_id, user, value, voted_at)
}
