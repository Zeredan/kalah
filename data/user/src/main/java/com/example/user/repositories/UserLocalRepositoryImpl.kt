package com.example.user.repositories

import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.user.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.map
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserLocalRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserLocalRepository {
    private val USER_KEY = stringPreferencesKey("language")
    private val gson = Gson()

    override fun getUser(): Flow<User?> {
        return dataStore.data.map {
            try {
                gson.fromJson(
                    it[USER_KEY] ?: "",
                    User::class.java
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun saveUser(user: User?) {
        dataStore.edit {
            it[USER_KEY] = gson.toJson(user)
        }
    }

}