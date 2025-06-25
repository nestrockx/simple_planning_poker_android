package com.wegielek.simpleplanningpoker.domain.models.post

data class CreateVoteRequest(
    val story_id: Int,
    val value: String,
)
