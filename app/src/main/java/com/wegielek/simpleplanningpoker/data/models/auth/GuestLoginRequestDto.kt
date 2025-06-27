package com.wegielek.simpleplanningpoker.data.models.auth

import com.wegielek.simpleplanningpoker.domain.models.auth.GuestLoginRequest

data class GuestLoginRequestDto(
    val nickname: String,
) {
    fun toDomain() = GuestLoginRequest(nickname)
}
