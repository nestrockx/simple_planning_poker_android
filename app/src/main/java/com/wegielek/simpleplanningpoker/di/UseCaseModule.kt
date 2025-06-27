package com.wegielek.simpleplanningpoker.di

import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository
import com.wegielek.simpleplanningpoker.domain.usecases.auth.GuestLoginUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.auth.LoginUseCase
import com.wegielek.simpleplanningpoker.domain.usecases.auth.RegisterUseCase
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
}
