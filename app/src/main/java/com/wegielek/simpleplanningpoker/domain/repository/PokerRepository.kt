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

    suspend fun getUser(user_id: Int): ParticipantUser

    suspend fun getRoom(code: String): Room

    suspend fun joinRoom(code: String): JoinRoomResponse

    suspend fun createRoom(
        name: String,
        type: String,
    ): Room

    suspend fun createStory(
        room_id: Int,
        title: String,
    ): Story

    suspend fun getStory(pk: Int): Story

    suspend fun deleteStory(pk: Int)

    suspend fun getStories(room_id: Int): List<Story>

    suspend fun createVote(
        story_id: Int,
        value: String,
    ): CreateVoteResponse

    suspend fun getVotes(story_id: Int): List<Vote>

    suspend fun deleteVote(story_id: Int)

    suspend fun updateNickname(nickname: String): Profile
}
