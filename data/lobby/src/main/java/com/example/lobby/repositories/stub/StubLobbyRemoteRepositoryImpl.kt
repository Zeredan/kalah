package com.example.lobby.repositories.stub

import com.example.lobby.model.Lobby
import com.example.lobby.repositories.LobbyRemoteRepository
import com.example.user.model.User
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class StubLobbyRemoteRepositoryImpl @Inject constructor(
) : LobbyRemoteRepository {
    val gameStartChannel = Channel<Boolean>()
    override suspend fun getAllLobbies(): List<Lobby> {
        return List(10) {
            Lobby(
                it,
                "lobbyname: $it",
                6,
                6,
                User("qwe", emptyMap(), null, null),
                null
            )
        }
    }

    override suspend fun joinLobby(lobbyId: Int, user: User): Lobby? {
        return Lobby(
            lobbyId,
            "lobbyname: $lobbyId",
            6,
            6,
            User("qwe", emptyMap(), null, null),
            user
        )
    }

    override suspend fun createLobby(lobby: Lobby): Lobby? {
        return lobby
    }

    override suspend fun startGame(lobbyId: Int) {
        gameStartChannel.send(true)
    }

    override suspend fun trackLobby(lobbyId: Int): Flow<Lobby> {
        return MutableStateFlow(
            Lobby(
                lobbyId,
                "lobbyname: $lobbyId",
                6,
                6,
                User("creator", emptyMap(), null, null),
                User("guest", emptyMap(), null, null),
            )
        )
    }

    override suspend fun trackGameStart(lobbyId: Int): Flow<Boolean> {
        return gameStartChannel.consumeAsFlow()
    }

}