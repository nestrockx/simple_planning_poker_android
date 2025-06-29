package com.wegielek.simpleplanningpoker.di

import com.wegielek.simpleplanningpoker.data.repository.PokerRepositoryImpl
import com.wegielek.simpleplanningpoker.data.repository.WebSocketRepositoryImpl
import com.wegielek.simpleplanningpoker.domain.repository.PokerRepository
import com.wegielek.simpleplanningpoker.domain.repository.WebSocketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPokerRepository(impl: PokerRepositoryImpl): PokerRepository

    @Binds
    abstract fun bindWebSocketRepository(impl: WebSocketRepositoryImpl): WebSocketRepository
}
