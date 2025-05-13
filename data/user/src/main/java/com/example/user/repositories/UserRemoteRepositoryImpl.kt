package com.example.user.repositories

import com.example.networking_core.ktor.KtorService
import com.example.user.model.User
import javax.inject.Inject

class UserRemoteRepositoryImpl @Inject constructor(
    private val ktorService: KtorService
) : UserRemoteRepository {
    override suspend fun getUserByCredentials(login: String, password: String): User? {
        return ktorService.getUserByCredentials(login, password)?.let{User(it)}
    }

    override suspend fun getAllUsers(): List<User> {
        return ktorService.getAllUsers().map { User(it) }
    }

    override suspend fun filterUsersByName(filter: String) : List<User> {
        return ktorService.filterUsersByName(filter).map { User(it) }
    }

    override suspend fun createUser(login: String, password: String) {
        ktorService.createUser(login, password)
    }

    override suspend fun updateUser(login: String, password: String, newUser: User) {
        ktorService.updateUser(login, password, newUser.toDTO())
    }
}