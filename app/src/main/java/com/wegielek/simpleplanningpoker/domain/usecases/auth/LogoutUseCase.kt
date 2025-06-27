package com.wegielek.simpleplanningpoker.domain.usecases.auth

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class LogoutUseCase(
    private val pokerRepository: PokerRepository,
) {
    suspend operator fun invoke() = pokerRepository.logout()
}
