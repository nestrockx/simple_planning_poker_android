package com.wegielek.simpleplanningpoker.domain.models.auth

data class RegisterRequest(
    val username: String,
    val nickname: String,
    val password: String,
)
