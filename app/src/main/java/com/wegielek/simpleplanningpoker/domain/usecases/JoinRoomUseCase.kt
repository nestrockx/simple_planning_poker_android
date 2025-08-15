package com.wegielek.simpleplanningpoker.domain.usecases

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class JoinRoomUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(code: String) = repository.joinRoom(code)
}
