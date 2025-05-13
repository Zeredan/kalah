package com.example.kalah_game.repositories.stub

import com.example.kalah_game.model.KalahGameState
import com.example.kalah_game.repositories.KalahGameRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.kalah_game.model.GameStatus

class StubKalahGameRemoteRepositoryImpl @Inject constructor(
) : KalahGameRemoteRepository {
    override suspend fun trackGame(gameId: Int): Flow<KalahGameState> {
        return flow{
            emit(KalahGameState(
                holesCount = 6,
                initialStonesCount = 4,
                player1Holes = mutableListOf(4, 4, 4, 4, 4, 4),
                player2Holes = mutableListOf(4, 4, 4, 4, 4, 4),
                player1Kalah = 0,
                player2Kalah = 0,
                currentGameStatus = GameStatus.PLAYING,
                currentPlayerInd = 1,
                player1Nickname = "Player1",
                player2Nickname = "Player2"
            ))
        }
    }

    override suspend fun makeMove(gameId: Int, nickname: String, holeIndex: Int) {

    }
}