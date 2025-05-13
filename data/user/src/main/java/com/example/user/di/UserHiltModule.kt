package com.example.user.di

import com.example.user.repositories.UserLocalRepository
import com.example.user.repositories.UserLocalRepositoryImpl
import com.example.user.repositories.UserRemoteRepository
import com.example.user.repositories.UserRemoteRepositoryImpl
import com.example.user.repositories.stub.StubUserRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserHiltModule {
    @Binds
    abstract fun bindUserRemoteRepository(impl: UserRemoteRepositoryImpl) : UserRemoteRepository

    @Binds
    abstract fun bindUserLocalRepository(impl: UserLocalRepositoryImpl) : UserLocalRepository
}