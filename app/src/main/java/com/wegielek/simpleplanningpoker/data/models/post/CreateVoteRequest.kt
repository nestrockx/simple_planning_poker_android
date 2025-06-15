package com.wegielek.simpleplanningpoker.data.models.post

data class CreateVoteRequest(
    val story_id: Int,
    val value: String,
)
