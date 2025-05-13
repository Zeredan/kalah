package com.example.user.repositories

import com.example.user.model.User
import kotlinx.coroutines.flow.Flow

interface UserLocalRepository {
    fun getUser() : Flow<User?>
    suspend fun saveUser(user: User?)
}