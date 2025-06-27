package com.wegielek.simpleplanningpoker.domain.usecases.auth

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class RegisterUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(
        username: String,
        nickname: String,
        password: String,
    ) = repository.register(username, nickname, password)
}
