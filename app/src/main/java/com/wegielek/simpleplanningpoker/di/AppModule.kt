package com.wegielek.simpleplanningpoker.di

import android.content.Context
import com.wegielek.simpleplanningpoker.data.remote.AuthInterceptor
import com.wegielek.simpleplanningpoker.data.remote.PokerApiService
import com.wegielek.simpleplanningpoker.prefs.Preferences.Companion.getTokenFromStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(
                AuthInterceptor {
                    getTokenFromStorage(context)
//                    "86babdcbb9cf17facd8248c2c47d7d2dba8f500f"
                },
            ).build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://simple-planning-poker-backend.onrender.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PokerApiService = retrofit.create(PokerApiService::class.java)
}
