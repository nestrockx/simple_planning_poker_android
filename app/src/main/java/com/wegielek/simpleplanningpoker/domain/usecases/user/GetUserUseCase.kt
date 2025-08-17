package com.wegielek.simpleplanningpoker.domain.usecases.user

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class GetUserUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(user_id: Int) = repository.getUser(user_id)
}
