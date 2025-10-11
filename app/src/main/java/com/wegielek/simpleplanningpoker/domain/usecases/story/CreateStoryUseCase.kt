package com.wegielek.simpleplanningpoker.domain.usecases.story

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class CreateStoryUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(
        roomId: Int,
        title: String,
    ) = repository.createStory(roomId, title)
}
