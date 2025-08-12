package com.wegielek.simpleplanningpoker.domain.usecases

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class DeleteVoteUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(story_id: Int) = repository.deleteVote(story_id)
}
