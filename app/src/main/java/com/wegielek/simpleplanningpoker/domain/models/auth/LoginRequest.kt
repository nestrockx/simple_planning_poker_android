package com.wegielek.simpleplanningpoker.domain.models.auth

data class LoginRequest(
    val username: String,
    val password: String,
)
