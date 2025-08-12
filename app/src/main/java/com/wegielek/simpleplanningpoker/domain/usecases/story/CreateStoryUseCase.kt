package com.wegielek.simpleplanningpoker.domain.usecases.story

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class CreateStoryUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(
        room_id: Int,
        title: String,
    ) = repository.createStory(room_id, title)
}
