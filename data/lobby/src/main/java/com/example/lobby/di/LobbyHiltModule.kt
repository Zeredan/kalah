package com.example.lobby.di

import com.example.lobby.repositories.LobbyRemoteRepository
import com.example.lobby.repositories.LobbyRemoteRepositoryImpl
import com.example.lobby.repositories.stub.StubLobbyRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LobbyHiltModule {
    @Binds
    abstract fun bindLobbyRemoteRepository(impl: LobbyRemoteRepositoryImpl) : LobbyRemoteRepository

}