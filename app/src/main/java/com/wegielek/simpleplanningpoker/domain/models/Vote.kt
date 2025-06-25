package com.wegielek.simpleplanningpoker.domain.models

import java.time.OffsetDateTime

data class Vote(
    val id: Int,
    val story_id: Int,
    val user: ParticipantUser,
    val value: String,
    val voted_at: OffsetDateTime,
)
