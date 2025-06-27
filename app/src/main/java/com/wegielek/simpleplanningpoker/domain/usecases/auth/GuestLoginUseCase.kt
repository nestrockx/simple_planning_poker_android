package com.wegielek.simpleplanningpoker.domain.usecases.auth

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class GuestLoginUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(nickname: String) = repository.guestLogin(nickname)
}
