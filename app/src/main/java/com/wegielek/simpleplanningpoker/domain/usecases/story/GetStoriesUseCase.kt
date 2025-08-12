package com.wegielek.simpleplanningpoker.domain.usecases.story

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class GetStoriesUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(room_id: Int) = repository.getStories(room_id)
}
