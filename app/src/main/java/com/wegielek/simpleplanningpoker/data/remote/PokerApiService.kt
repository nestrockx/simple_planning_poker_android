package com.wegielek.simpleplanningpoker.data.remote

import com.wegielek.simpleplanningpoker.domain.models.JoinRoomResponse
import com.wegielek.simpleplanningpoker.domain.models.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.Profile
import com.wegielek.simpleplanningpoker.domain.models.Room
import com.wegielek.simpleplanningpoker.domain.models.RoomResponse
import com.wegielek.simpleplanningpoker.domain.models.Story
import com.wegielek.simpleplanningpoker.domain.models.Vote
import com.wegielek.simpleplanningpoker.domain.models.auth.AuthResponse
import com.wegielek.simpleplanningpoker.domain.models.auth.GuestLoginRequest
import com.wegielek.simpleplanningpoker.domain.models.auth.LoginRequest
import com.wegielek.simpleplanningpoker.domain.models.auth.RegisterRequest
import com.wegielek.simpleplanningpoker.domain.models.post.CreateRoomRequest
import com.wegielek.simpleplanningpoker.domain.models.post.CreateStoryRequest
import com.wegielek.simpleplanningpoker.domain.models.post.CreateVoteRequest
import com.wegielek.simpleplanningpoker.domain.models.post.UpdateProfileRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PokerApiService {
    // Auth
    @POST("/auth/login/")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): AuthResponse

    @POST("/auth/register/")
    suspend fun register(
        @Body registerRequest: RegisterRequest,
    ): AuthResponse

    @POST("/auth/guestlogin/")
    suspend fun guestLogin(
        @Body guestLoginRequest: GuestLoginRequest,
    ): AuthResponse

    @POST("/auth/logout/")
    suspend fun logout()

    // User
    @GET("/api/userinfo/")
    suspend fun getUserInfo(): ParticipantUser

    @GET("/api/users/{user_id}/")
    suspend fun getUserInfo(
        @Path("user_id") user_id: Int,
    ): ParticipantUser

    // Room
    @GET("/api/rooms/")
    suspend fun getRooms(): RoomResponse

    @POST("/api/rooms/")
    suspend fun createRoom(
        @Body room: CreateRoomRequest,
    ): Room

    @GET("/api/rooms/{code}/")
    suspend fun getRoom(
        @Path("code") code: String,
    ): Room

    @GET("/api/rooms/{code}/join/")
    suspend fun joinRoom(
        @Path("code") code: String,
    ): JoinRoomResponse

    // Story
    @POST("/api/stories/")
    suspend fun createStory(
        @Body story: CreateStoryRequest,
    ): Story

    @GET("/api/stories/{room_id}/")
    suspend fun getRoomStory(
        @Path("room_id") room_id: Int,
    ): Story

    @GET("/api/stories/{pk}/")
    suspend fun getStory(
        @Path("pk") pk: Int,
    ): Story

    @DELETE("/api/stories/{pk}/delete")
    suspend fun deleteStory(
        @Path("pk") pk: Int,
    )

    // Vote
    @POST("/api/votes/")
    suspend fun createVote(
        @Body vote: CreateVoteRequest,
    )

    @GET("/api/votes/")
    suspend fun getVotes(): List<Vote>

    @GET("/api/votes/{story_id}/")
    suspend fun getVote(
        @Path("story_id") story_id: Int,
    ): Vote

    @DELETE("/api/votes/{story_id}/delete/")
    suspend fun deleteVote()

    // Profile
    @POST("/api/profile/")
    suspend fun updateNickname(
        @Body profile: UpdateProfileRequest,
    ): Profile
}
