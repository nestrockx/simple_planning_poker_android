package com.wegielek.simpleplanningpoker.domain.repository

import com.wegielek.simpleplanningpoker.domain.models.post.CreateVoteResponse
import com.wegielek.simpleplanningpoker.domain.models.room.JoinRoomResponse
import com.wegielek.simpleplanningpoker.domain.models.room.ParticipantUser
import com.wegielek.simpleplanningpoker.domain.models.room.Profile
import com.wegielek.simpleplanningpoker.domain.models.room.Room
import com.wegielek.simpleplanningpoker.domain.models.room.Story
import com.wegielek.simpleplanningpoker.domain.models.room.Vote

interface PokerRepository {
    suspend fun login(
        username: String,
        password: String,
    ): Boolean

    suspend fun register(
        username: String,
        nickname: String,
        password: String,
    ): Boolean

    suspend fun guestLogin(nickname: String): Boolean

    suspend fun logout()

    suspend fun getUserInfo(): ParticipantUser

    suspend fun getUser(userId: Int): ParticipantUser

    suspend fun getRoom(code: String): Room

    suspend fun joinRoom(code: String): JoinRoomResponse

    suspend fun createRoom(
        name: String,
        type: String,
    ): Room

    suspend fun createStory(
        roomId: Int,
        title: String,
    ): Story

    suspend fun getStory(pk: Int): Story

    suspend fun deleteStory(pk: Int)

    suspend fun getStories(roomId: Int): List<Story>

    suspend fun createVote(
        storyId: Int,
        value: String,
    ): CreateVoteResponse

    suspend fun getVotes(storyId: Int): List<Vote>

    suspend fun deleteVote(storyId: Int)

    suspend fun updateNickname(nickname: String): Profile
}
