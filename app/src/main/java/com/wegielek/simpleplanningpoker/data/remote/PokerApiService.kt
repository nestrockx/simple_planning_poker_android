package com.wegielek.simpleplanningpoker.data.remote

import com.wegielek.simpleplanningpoker.data.models.auth.AuthResponseDto
import com.wegielek.simpleplanningpoker.data.models.auth.GuestLoginRequestDto
import com.wegielek.simpleplanningpoker.data.models.auth.LoginRequestDto
import com.wegielek.simpleplanningpoker.data.models.auth.RegisterRequestDto
import com.wegielek.simpleplanningpoker.data.models.post.CreateRoomRequestDto
import com.wegielek.simpleplanningpoker.data.models.post.CreateVoteRequestDto
import com.wegielek.simpleplanningpoker.data.models.post.UpdateProfileRequestDto
import com.wegielek.simpleplanningpoker.data.models.room.JoinRoomResponseDto
import com.wegielek.simpleplanningpoker.data.models.room.ParticipantUserDto
import com.wegielek.simpleplanningpoker.data.models.room.RoomDto
import com.wegielek.simpleplanningpoker.data.models.room.RoomResponseDto
import com.wegielek.simpleplanningpoker.data.models.room.StoryDto
import com.wegielek.simpleplanningpoker.data.models.room.VoteDto
import com.wegielek.simpleplanningpoker.domain.models.post.CreateStoryRequest
import com.wegielek.simpleplanningpoker.domain.models.room.Profile
import com.wegielek.simpleplanningpoker.domain.models.room.Vote
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PokerApiService {
    // Auth
    @POST("/auth/login/")
    suspend fun login(
        @Body loginRequest: LoginRequestDto,
    ): AuthResponseDto

    @POST("/auth/register/")
    suspend fun register(
        @Body registerRequest: RegisterRequestDto,
    ): AuthResponseDto

    @POST("/auth/guestlogin/")
    suspend fun guestLogin(
        @Body guestLoginRequest: GuestLoginRequestDto,
    ): AuthResponseDto

    @POST("/auth/logout/")
    suspend fun logout()

    // User
    @GET("/api/userinfo/")
    suspend fun getUserInfo(): ParticipantUserDto

    @GET("/api/users/{user_id}/")
    suspend fun getUserInfo(
        @Path("user_id") user_id: Int,
    ): ParticipantUserDto

    // Room
    @GET("/api/rooms/")
    suspend fun getRooms(): RoomResponseDto

    @POST("/api/rooms/")
    suspend fun createRoom(
        @Body room: CreateRoomRequestDto,
    ): RoomDto

    @GET("/api/rooms/{code}/")
    suspend fun getRoom(
        @Path("code") code: String,
    ): RoomDto

    @GET("/api/rooms/{code}/join/")
    suspend fun joinRoom(
        @Path("code") code: String,
    ): JoinRoomResponseDto

    // Story
    @POST("/api/stories/")
    suspend fun createStory(
        @Body story: CreateStoryRequest,
    ): StoryDto

    @GET("/api/stories/{room_id}/")
    suspend fun getRoomStory(
        @Path("room_id") room_id: Int,
    ): StoryDto

    @GET("/api/stories/{pk}/")
    suspend fun getStory(
        @Path("pk") pk: Int,
    ): StoryDto

    @DELETE("/api/stories/{pk}/delete")
    suspend fun deleteStory(
        @Path("pk") pk: Int,
    )

    // Vote
    @POST("/api/votes/")
    suspend fun createVote(
        @Body vote: CreateVoteRequestDto,
    )

    @GET("/api/votes/")
    suspend fun getVotes(): List<VoteDto>

    @GET("/api/votes/{story_id}/")
    suspend fun getVote(
        @Path("story_id") story_id: Int,
    ): Vote

    @DELETE("/api/votes/{story_id}/delete/")
    suspend fun deleteVote()

    // Profile
    @POST("/api/profile/")
    suspend fun updateNickname(
        @Body profile: UpdateProfileRequestDto,
    ): Profile

    // empty
    @GET("/api/empty/")
    suspend fun empty(): Unit
}
