package com.example.kalah_game.di

import com.example.kalah_game.repositories.KalahGameRemoteRepository
import com.example.kalah_game.repositories.KalahGameRemoteRepositoryImpl
import com.example.kalah_game.repositories.stub.StubKalahGameRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class KalahGameHiltModule {
    @Binds
    abstract fun bindKalahGameRemoteRepository(impl: KalahGameRemoteRepositoryImpl) : KalahGameRemoteRepository

}