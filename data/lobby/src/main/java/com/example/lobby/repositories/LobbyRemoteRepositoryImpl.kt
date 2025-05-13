package com.example.lobby.repositories

import com.example.networking_core.ktor.KtorService
import com.example.lobby.model.Lobby
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.user.model.User

class LobbyRemoteRepositoryImpl @Inject constructor(
    private val ktorService: KtorService
) : LobbyRemoteRepository {
    override suspend fun getAllLobbies(): List<Lobby> {
        return ktorService.getAllLobbies().map{ Lobby(it) }
    }

    override suspend fun joinLobby(lobbyId: Int, user: User): Lobby? {
        return ktorService.joinLobby(lobbyId, user.toDTO())?.run { Lobby(this) }
    }

    override suspend fun createLobby(lobby: Lobby): Lobby? {
        return ktorService.createLobby(lobby.toDTO())?.run{Lobby(this)}
    }

    override suspend fun startGame(lobbyId: Int) {
        ktorService.startGame(lobbyId)
    }

    override suspend fun trackLobby(lobbyId: Int): Flow<Lobby> {
        return ktorService.trackLobby(lobbyId).map { lobbyDTO ->
            Lobby(lobbyDTO)
        }
    }

    override suspend fun trackGameStart(lobbyId: Int): Flow<Boolean> {
        return ktorService.trackGame(lobbyId).map { gameState ->
            true
        }
    }
}