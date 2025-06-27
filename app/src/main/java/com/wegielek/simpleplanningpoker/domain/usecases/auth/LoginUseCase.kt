package com.wegielek.simpleplanningpoker.domain.usecases.auth

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class LoginUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(
        username: String,
        password: String,
    ) = repository.login(username, password)
}
