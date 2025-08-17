package com.wegielek.simpleplanningpoker.domain.usecases.user

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class GetUserInfoUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke() = repository.getUserInfo()
}
