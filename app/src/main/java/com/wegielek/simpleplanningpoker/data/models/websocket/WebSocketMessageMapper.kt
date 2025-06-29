package com.wegielek.simpleplanningpoker.data.models.websocket

import com.wegielek.simpleplanningpoker.domain.models.websocket.AddStory
import com.wegielek.simpleplanningpoker.domain.models.websocket.ParticipantAdd
import com.wegielek.simpleplanningpoker.domain.models.websocket.ParticipantRemove
import com.wegielek.simpleplanningpoker.domain.models.websocket.Participants
import com.wegielek.simpleplanningpoker.domain.models.websocket.RemoveStory
import com.wegielek.simpleplanningpoker.domain.models.websocket.Reset
import com.wegielek.simpleplanningpoker.domain.models.websocket.ResetVotes
import com.wegielek.simpleplanningpoker.domain.models.websocket.Reveal
import com.wegielek.simpleplanningpoker.domain.models.websocket.RevealVotes
import com.wegielek.simpleplanningpoker.domain.models.websocket.Story
import com.wegielek.simpleplanningpoker.domain.models.websocket.Summon
import com.wegielek.simpleplanningpoker.domain.models.websocket.Vote
import com.wegielek.simpleplanningpoker.domain.models.websocket.VoteUpdate
import com.wegielek.simpleplanningpoker.domain.models.websocket.WebSocketMessage

fun WebSocketMessageDto.toDomain(): WebSocketMessage =
    when (this) {
        is ParticipantAddDto -> ParticipantAdd(type, participants.toDomain())
        is ParticipantRemoveDto -> ParticipantRemove(type, participants.toDomain())
        is RevealVotesDto -> RevealVotes(type, reveal.toDomain())
        is ResetVotesDto -> ResetVotes(type, reset.toDomain())
        is VoteUpdateDto -> VoteUpdate(type, vote.toDomain())
        is AddStoryDto -> AddStory(type, story.toDomain())
        is RemoveStoryDto -> RemoveStory(type, story.toDomain())
        is SummonDto -> Summon(type, story.toDomain())
    }

fun ParticipantsDto.toDomain() =
    Participants(
        id = id,
        username = username,
    )

fun RevealDto.toDomain() =
    Reveal(
        story_id = story_id,
        username = username,
        value = value,
    )

fun ResetDto.toDomain() =
    Reset(
        story_id = story_id,
        username = username,
    )

fun VoteDto.toDomain() =
    Vote(
        story_id = story_id,
        username = username,
        value = value,
    )

fun StoryDto.toDomain() =
    Story(
        id = id,
        title = title,
        is_revealed = is_revealed,
    )
