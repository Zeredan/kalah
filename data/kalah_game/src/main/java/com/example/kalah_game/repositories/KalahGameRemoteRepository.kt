package com.example.kalah_game.repositories

import com.example.kalah_game.model.KalahGameState
import kotlinx.coroutines.flow.Flow

interface KalahGameRemoteRepository {
    suspend fun trackGame(gameId: Int) : Flow<KalahGameState>
    suspend fun makeMove(gameId: Int, nickname: String, holeIndex: Int)
}