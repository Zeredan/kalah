package com.example.user.repositories

import com.example.user.model.User

interface UserRemoteRepository {
    suspend fun getUserByCredentials(login: String, password: String) : User?
    suspend fun getAllUsers() : List<User>
    suspend fun filterUsersByName(filter: String) : List<User>

    suspend fun createUser(login: String, password: String)
    suspend fun updateUser(login: String, password: String, newUser: User)
}