package com.wegielek.simpleplanningpoker.domain.usecases

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class CreateVoteUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(
        story_id: Int,
        value: String,
    ) = repository.createVote(story_id, value)
}
