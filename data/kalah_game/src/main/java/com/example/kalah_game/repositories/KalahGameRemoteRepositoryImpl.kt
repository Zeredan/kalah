package com.example.kalah_game.repositories

import com.example.kalah_game.model.KalahGameState
import com.example.kalah_game.repositories.KalahGameRemoteRepository
import com.example.networking_core.ktor.KtorService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KalahGameRemoteRepositoryImpl @Inject constructor(
    private val ktorService: KtorService
) : KalahGameRemoteRepository {
    override suspend fun trackGame(gameId: Int): Flow<KalahGameState> {
        return ktorService.trackGame(gameId).map { KalahGameState(it) }
    }

    override suspend fun makeMove(gameId: Int, nickname: String, holeIndex: Int) {
        ktorService.makeMove(gameId, nickname, holeIndex)
    }

}