package com.example.user.repositories.stub

import com.example.networking_core.ktor.KtorService
import com.example.user.model.User
import com.example.user.repositories.UserRemoteRepository
import javax.inject.Inject

class StubUserRemoteRepositoryImpl @Inject constructor(
) : UserRemoteRepository {
    override suspend fun getUserByCredentials(login: String, password: String): User? {
        return User(
            login,
            mapOf(
                "easy" to 5,
                "medium" to 1,
                "hard" to 4,
            ),
            login,
            password
        )
    }

    override suspend fun getAllUsers(): List<User> {
        return emptyList()
    }

    override suspend fun filterUsersByName(filter: String) : List<User> {
        return emptyList()
    }

    override suspend fun createUser(login: String, password: String) {

    }

    override suspend fun updateUser(login: String, password: String, newUser: User) {

    }
}