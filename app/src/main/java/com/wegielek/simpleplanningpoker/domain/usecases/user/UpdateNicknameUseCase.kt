package com.wegielek.simpleplanningpoker.domain.usecases.user

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository

class UpdateNicknameUseCase(
    private val repository: PokerRepository,
) {
    suspend operator fun invoke(nickname: String) = repository.updateNickname(nickname)
}
