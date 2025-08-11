package com.wegielek.simpleplanningpoker.domain.usecases

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class CreateStoryUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(room_id: Int) = repository.createStory(room_id)
}
