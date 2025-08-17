package com.wegielek.simpleplanningpoker.di

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository
import com.wegielek.simpleplanningpoker.domain.repository.WebSocketRepository
import com.wegielek.simpleplanningpoker.domain.usecases.auth.GuestLoginUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.auth.LoginUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.auth.LogoutUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.auth.RegisterUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.room.CreateRoomUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.room.GetRoomUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.room.JoinRoomUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.story.CreateStoryUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.story.DeleteStoryUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.story.GetStoriesUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.story.GetStoryUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.user.GetUserInfoUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.user.GetUserUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.user.UpdateNicknameUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.vote.CreateVoteUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.vote.DeleteVoteUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.vote.GetVotesUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.websocket.WebSocketUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideWebSocketUseCase(webSocketRepository: WebSocketRepository): WebSocketUseCase = WebSocketUseCase(webSocketRepository)

    @Provides
    fun provideLoginUseCase(pokerRepository: PokerRepository): LoginUseCase = LoginUseCase(pokerRepository)

    @Provides
    fun provideRegisterUseCase(pokerRepository: PokerRepository): RegisterUseCase = RegisterUseCase(pokerRepository)

    @Provides
    fun provideGuestLoginUseCase(pokerRepository: PokerRepository): GuestLoginUseCase = GuestLoginUseCase(pokerRepository)

    @Provides
    fun provideLogoutUseCase(pokerRepository: PokerRepository): LogoutUseCase = LogoutUseCase(pokerRepository)

    @Provides
    fun provideUserInfoUseCase(pokerRepository: PokerRepository): GetUserInfoUseCase = GetUserInfoUseCase(pokerRepository)

    @Provides
    fun provideGetUserUseCase(pokerRepository: PokerRepository): GetUserUseCase = GetUserUseCase(pokerRepository)

    @Provides
    fun provideGetRoomUseCase(pokerRepository: PokerRepository): GetRoomUseCase = GetRoomUseCase(pokerRepository)

    @Provides
    fun provideCreateRoomUseCase(pokerRepository: PokerRepository): CreateRoomUseCase = CreateRoomUseCase(pokerRepository)

    @Provides
    fun provideCreateStoryUseCase(pokerRepository: PokerRepository): CreateStoryUseCase = CreateStoryUseCase(pokerRepository)

    @Provides
    fun provideGetStoriesUseCase(pokerRepository: PokerRepository): GetStoriesUseCase = GetStoriesUseCase(pokerRepository)

    @Provides
    fun provideGetStoryUseCase(pokerRepository: PokerRepository): GetStoryUseCase = GetStoryUseCase(pokerRepository)

    @Provides
    fun provideDeleteStoryUseCase(pokerRepository: PokerRepository): DeleteStoryUseCase = DeleteStoryUseCase(pokerRepository)

    @Provides
    fun provideCreateVoteUseCase(pokerRepository: PokerRepository): CreateVoteUseCase = CreateVoteUseCase(pokerRepository)

    @Provides
    fun provideDeleteVoteUseCase(pokerRepository: PokerRepository): DeleteVoteUseCase = DeleteVoteUseCase(pokerRepository)

    @Provides
    fun provideGetVotesUseCase(pokerRepository: PokerRepository): GetVotesUseCase = GetVotesUseCase(pokerRepository)

    @Provides
    fun provideUpdateNicknameUseCase(pokerRepository: PokerRepository): UpdateNicknameUseCase = UpdateNicknameUseCase(pokerRepository)

    @Provides
    fun provideJoinRoomUseCase(pokerRepository: PokerRepository): JoinRoomUseCase = JoinRoomUseCase(pokerRepository)
}
