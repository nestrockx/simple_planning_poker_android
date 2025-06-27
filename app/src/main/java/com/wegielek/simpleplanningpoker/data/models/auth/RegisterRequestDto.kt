package com.wegielek.simpleplanningpoker.data.models.auth

import com.wegielek.simpleplanningpoker.domain.models.auth.RegisterRequest

data class RegisterRequestDto(
    val username: String,
    val nickname: String,
    val password: String,
) {
    fun toDomain() = RegisterRequest(username, nickname, password)
}
