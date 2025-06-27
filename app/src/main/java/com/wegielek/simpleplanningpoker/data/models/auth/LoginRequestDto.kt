package com.wegielek.simpleplanningpoker.data.models.auth

import com.wegielek.simpleplanningpoker.domain.models.auth.LoginRequest

data class LoginRequestDto(
    val username: String,
    val password: String,
) {
    fun toDomain() = LoginRequest(username, password)
}
