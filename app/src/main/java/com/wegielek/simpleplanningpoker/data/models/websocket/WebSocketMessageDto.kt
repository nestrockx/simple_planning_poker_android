package com.wegielek.simpleplanningpoker.data.models.websocket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
sealed class WebSocketMessageDto {
    abstract val type: String
}

@Serializable
@SerialName("participant_add")
data class ParticipantAddDto(
    override val type: String,
    val participants: ParticipantsDto,
) : WebSocketMessageDto()

@Serializable
@SerialName("participant_remove")
data class ParticipantRemoveDto(
    override val type: String,
    val participants: ParticipantsDto,
) : WebSocketMessageDto()

@Serializable
data class ParticipantsDto(
    val id: Int,
    val username: String,
)

@Serializable
@SerialName("reveal_votes")
data class RevealVotesDto(
    override val type: String,
    val reveal: RevealDto,
) : WebSocketMessageDto()

@Serializable
data class RevealDto(
    val story_id: Int,
    val username: String,
    val value: Boolean,
)

@Serializable
@SerialName("reset_votes")
data class ResetVotesDto(
    override val type: String,
    val reset: ResetDto,
) : WebSocketMessageDto()

@Serializable
data class ResetDto(
    val story_id: Int,
    val username: String,
)

@Serializable
@SerialName("vote_update")
data class VoteUpdateDto(
    override val type: String,
    val vote: VoteDto,
) : WebSocketMessageDto()

@Serializable
data class VoteDto(
    val story_id: Int,
    val username: String,
    val value: JsonElement,
)

@Serializable
@SerialName("add_story")
data class AddStoryDto(
    override val type: String,
    val story: StoryDto,
) : WebSocketMessageDto()

@Serializable
@SerialName("remove_story")
data class RemoveStoryDto(
    override val type: String,
    val story: StoryDto,
) : WebSocketMessageDto()

@Serializable
@SerialName("summon")
data class SummonDto(
    override val type: String,
    val story: StoryDto,
) : WebSocketMessageDto()

@Serializable
data class StoryDto(
    val id: Int,
    val title: String,
    val is_revealed: Boolean,
)
