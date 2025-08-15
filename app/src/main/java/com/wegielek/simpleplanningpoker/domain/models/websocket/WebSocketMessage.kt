package com.wegielek.simpleplanningpoker.domain.models.websocket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
sealed class WebSocketMessage {
    abstract val type: String
}

@Serializable
@SerialName("participant_add")
data class ParticipantAdd(
    override val type: String,
    val participants: Participants,
) : WebSocketMessage()

@Serializable
@SerialName("participant_remove")
data class ParticipantRemove(
    override val type: String,
    val participants: Participants,
) : WebSocketMessage()

@Serializable
data class Participants(
    val id: Int,
    val username: String,
)

@Serializable
@SerialName("reveal_votes")
data class RevealVotes(
    override val type: String,
    val reveal: Reveal,
) : WebSocketMessage()

@Serializable
data class Reveal(
    val story_id: Int,
    val username: String,
    val value: Boolean,
)

@Serializable
@SerialName("reset_votes")
data class ResetVotes(
    override val type: String,
    val reset: Reset,
) : WebSocketMessage()

@Serializable
data class Reset(
    val story_id: Int,
    val username: String,
)

@Serializable
@SerialName("vote_update")
data class VoteUpdate(
    override val type: String,
    val vote: Vote,
) : WebSocketMessage()

@Serializable
data class Vote(
    val story_id: Int,
    val username: String,
    val value: JsonElement,
)

@Serializable
@SerialName("add_story")
data class AddStory(
    override val type: String,
    val story: Story,
) : WebSocketMessage()

@Serializable
@SerialName("remove_story")
data class RemoveStory(
    override val type: String,
    val story: Story,
) : WebSocketMessage()

@Serializable
@SerialName("summon")
data class Summon(
    override val type: String,
    val story: Story,
) : WebSocketMessage()

@Serializable
data class Story(
    val id: Int,
    val title: String,
    val is_revealed: Boolean,
)
