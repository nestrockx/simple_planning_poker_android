package com.wegielek.simpleplanningpoker.domain.usecases

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class GetVotesUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(story_id: Int) = repository.getVotes(story_id)
}
