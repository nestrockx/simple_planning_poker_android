package com.wegielek.simpleplanningpoker.domain.usecases.room

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class CreateRoomUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(
        name: String,
        type: String,
    ) = repository.createRoom(name, type)
}
