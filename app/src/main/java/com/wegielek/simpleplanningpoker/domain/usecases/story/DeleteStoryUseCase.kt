package com.wegielek.simpleplanningpoker.domain.usecases.story

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class DeleteStoryUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(pk: Int) = repository.deleteStory(pk)
}
