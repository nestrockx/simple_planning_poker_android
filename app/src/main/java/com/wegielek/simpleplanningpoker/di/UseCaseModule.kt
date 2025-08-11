package com.wegielek.simpleplanningpoker.di

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository
import com.wegielek.simpleplanningpoker.domain.repository.WebSocketRepository
import com.wegielek.simpleplanningpoker.domain.usecases.CreateRoomUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.CreateStoryUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.GetRoomUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.GetStoriesUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.GetVotesUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.UserInfoUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.auth.GuestLoginUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.auth.LoginUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.auth.LogoutUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.auth.RegisterUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.websocket.WebSocketUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideLoginUseCase(pokerRepository: PokerRepository): LoginUseCase = LoginUseCase(pokerRepository)

    @Provides
    fun provideRegisterUseCase(pokerRepository: PokerRepository): RegisterUseCase = RegisterUseCase(pokerRepository)

    @Provides
    fun provideGuestLoginUseCase(pokerRepository: PokerRepository): GuestLoginUseCase = GuestLoginUseCase(pokerRepository)

    @Provides
    fun provideLogoutUseCase(pokerRepository: PokerRepository): LogoutUseCase = LogoutUseCase(pokerRepository)

    @Provides
    fun provideUserInfoUseCase(pokerRepository: PokerRepository): UserInfoUseCase = UserInfoUseCase(pokerRepository)

    @Provides
    fun provideGetRoomUseCase(pokerRepository: PokerRepository): GetRoomUseCase = GetRoomUseCase(pokerRepository)

    @Provides
    fun provideCreateRoomUseCase(pokerRepository: PokerRepository): CreateRoomUseCase = CreateRoomUseCase(pokerRepository)

    @Provides
    fun provideCreateStoryUseCase(pokerRepository: PokerRepository): CreateStoryUseCase = CreateStoryUseCase(pokerRepository)

    @Provides
    fun provideGetStoriesUseCase(pokerRepository: PokerRepository): GetStoriesUseCase = GetStoriesUseCase(pokerRepository)

    @Provides
    fun provideGetVotesUseCase(pokerRepository: PokerRepository): GetVotesUseCase = GetVotesUseCase(pokerRepository)

    @Provides
    fun provideWebSocketUseCase(webSocketRepository: WebSocketRepository): WebSocketUseCase = WebSocketUseCase(webSocketRepository)
}
