package com.wegielek.simpleplanningpoker.data.models.auth

import com.wegielek.simpleplanningpoker.domain.models.auth.AuthResponse

data class AuthResponseDto(
    val accessToken: String,
) {
    fun toDomain() = AuthResponse(accessToken)
}
