package com.example.lobby.repositories

import com.example.lobby.model.Lobby
import com.example.networking_core.DTOs.LobbyDTO
import com.example.networking_core.ktor.di.KtorNetworkSettings
import com.example.user.model.User
import kotlinx.coroutines.flow.Flow

interface LobbyRemoteRepository {
    suspend fun getAllLobbies() : List<Lobby>
    suspend fun joinLobby(lobbyId: Int, user: User): Lobby?
    suspend fun createLobby(lobby: Lobby): Lobby?
    suspend fun startGame(lobbyId: Int)
    suspend fun trackLobby(lobbyId: Int): Flow<Lobby>
    suspend fun trackGameStart(lobbyId: Int): Flow<Boolean>
}