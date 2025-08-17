package com.wegielek.simpleplanningpoker.data.repository

import android.content.Context
import com.wegielek.simpleplanningpoker.data.models.auth.GuestLoginRequestDto
import com.wegielek.simpleplanningpoker.data.models.auth.LoginRequestDto
import com.wegielek.simpleplanningpoker.data.models.auth.RegisterRequestDto
import com.wegielek.simpleplanningpoker.data.models.post.CreateRoomRequestDto
import com.wegielek.simpleplanningpoker.data.models.post.CreateStoryRequestDto
import com.wegielek.simpleplanningpoker.data.models.post.CreateVoteRequestDto
import com.wegielek.simpleplanningpoker.data.models.post.UpdateProfileRequestDto
import com.wegielek.simpleplanningpoker.data.remote.PokerApiService
import com.wegielek.simpleplanningpoker.domain.models.post.CreateVoteResponse
import com.wegielek.simpleplanningpoker.domain.models.room.JoinRoomResponse
import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.room.Profile
import com.wegielek.simpleplanningpoker.domain.models.room.Room
import com.wegielek.simpleplanningpoker.domain.models.room.Story
import com.wegielek.simpleplanningpoker.domain.models.room.Vote
import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository
import com.wegielek.simpleplanningpoker.prefs.Preferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PokerRepositoryImpl
    @Inject
    constructor(
        private val pokerApiService: PokerApiService,
        @param:ApplicationContext private val context: Context,
    ) : PokerRepository {
        // Auth
        override suspend fun login(
            username: String,
            password: String,
        ): Boolean =
            pokerApiService
                .login(LoginRequestDto(username, password))
                .toDomain()
                .also {
                    Preferences.saveToken(context, it.accessToken)
                }.accessToken
                .isNotEmpty()

        override suspend fun register(
            username: String,
            nickname: String,
            password: String,
        ): Boolean =
            pokerApiService
                .register(RegisterRequestDto(username, nickname, password))
                .toDomain()
                .also {
                    Preferences.saveToken(context, it.accessToken)
                }.accessToken
                .isNotEmpty()

        override suspend fun guestLogin(nickname: String): Boolean =
            pokerApiService
                .guestLogin(GuestLoginRequestDto(nickname))
                .toDomain()
                .also {
                    Preferences.saveToken(context, it.accessToken)
                }.accessToken
                .isNotEmpty()

        override suspend fun logout() = pokerApiService.logout()

        // User
        override suspend fun getUserInfo(): ParticipantUser = pokerApiService.getUserInfo().toDomain()

        override suspend fun getUser(user_id: Int): ParticipantUser = pokerApiService.getUser(user_id).toDomain()

        // Room
        override suspend fun getRoom(code: String): Room = pokerApiService.getRoom(code).toDomain()

        override suspend fun joinRoom(code: String): JoinRoomResponse = pokerApiService.joinRoom(code).toDomain()

        override suspend fun createRoom(
            name: String,
            type: String,
        ): Room =
            pokerApiService
                .createRoom(CreateRoomRequestDto(name, type))
                .toDomain()

        override suspend fun createStory(
            room_id: Int,
            title: String,
        ): Story =
            pokerApiService
                .createStory(CreateStoryRequestDto(room_id, title))
                .toDomain()

        override suspend fun getStory(pk: Int): Story = pokerApiService.getStory(pk).toDomain()

        override suspend fun deleteStory(pk: Int) = pokerApiService.deleteStory(pk)

        override suspend fun getStories(room_id: Int): List<Story> =
            pokerApiService.getRoomStories(room_id).map {
                it.toDomain()
            }

        override suspend fun createVote(
            story_id: Int,
            value: String,
        ): CreateVoteResponse =
            pokerApiService
                .createVote(CreateVoteRequestDto(story_id, value))
                .toDomain()

        override suspend fun getVotes(story_id: Int): List<Vote> =
            pokerApiService.getVotes(story_id).map {
                it.toDomain()
            }

        override suspend fun deleteVote(story_id: Int) = pokerApiService.deleteVote(story_id)

        override suspend fun updateNickname(nickname: String): Profile =
            pokerApiService.updateNickname(UpdateProfileRequestDto(nickname)).toDomain()
    }
