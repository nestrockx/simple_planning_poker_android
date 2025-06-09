package com.wegielek.simpleplanningpoker.data.api

import com.wegielek.simpleplanningpoker.data.models.ParticipantUser
import com.wegielek.simpleplanningpoker.data.models.Room
import com.wegielek.simpleplanningpoker.data.models.auth.AuthResponse
import com.wegielek.simpleplanningpoker.data.models.auth.GuestLoginRequest
import com.wegielek.simpleplanningpoker.data.models.auth.LoginRequest
import com.wegielek.simpleplanningpoker.data.models.auth.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PokerApiService {
    // Auth
    @POST("/auth/login/")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): AuthResponse

    @POST("/auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest,
    ): AuthResponse

    @POST("/auth/guestlogin")
    suspend fun guestLogin(
        @Body guestLoginRequest: GuestLoginRequest,
    ): AuthResponse

    @POST("/auth/logout")
    suspend fun logout()

    // Room
    @GET("/api/userinfo/")
    suspend fun getUserInfo(): ParticipantUser

    @GET("/api/rooms/")
    suspend fun getRooms(): List<Room>
}
