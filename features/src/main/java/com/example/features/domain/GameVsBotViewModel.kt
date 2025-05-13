package com.example.features.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.kalah_game.model.KalahGameState
import com.example.user.repositories.UserLocalRepository
import com.example.user.repositories.UserRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameVsBotViewModel @Inject constructor(
    private val userRemoteRepository: UserRemoteRepository,
    private val userLocalRepository: UserLocalRepository,
): PlayViewModel() {
    val kalahGameState = MutableStateFlow<KalahGameState?>(null)
    val currentPlayer = MutableStateFlow(1)
    var isMakingMove by mutableStateOf(false)

    fun startGame() {
        if (kalahGameState.value == null) {
            kalahGameState.value = KalahGameState(
                holesCount = this.holesCount.value,
                initialStonesCount = this.stoneCount.value,
                currentPlayerInd = 1,
                currentGameStatus = com.example.kalah_game.model.GameStatus.PLAYING,
                player1Nickname = "Me",
                player2Nickname = "Bot",
            )
            viewModelScope.launch {
                while(kalahGameState.value != null) {
                    delay(1000)
                    if (currentPlayer.value == 2) {
                        makeMovePlayer2(kalahGameState.value!!.player2Holes.indexOfFirst { it != 0 })
                    }
                }
            }
        }
    }

    fun stopGame() {
        kalahGameState.value = null
        currentPlayer.value = 1
        isMakingMove = false
    }

    private suspend fun makeMovePlayer1(holeIndex: Int) {
        try {
            if (kalahGameState.value!!.player1Holes[holeIndex] == 0) return
            if (isMakingMove) return
            isMakingMove = true
            kalahGameState.value!!.deepCopy().run tmpKalahState@{
                var stones = player1Holes[holeIndex]
                var (currentIndex, isPlayer1) = holeIndex to true
                player1Holes[holeIndex] = 0
                var isLastInKalah = false
                var isLastInEmpty = false
                repeat(stones) { ind ->
                    if (isPlayer1) {
                        if (currentIndex < holesCount - 1) {
                            ++currentIndex
                            ++player1Holes[currentIndex]
                        } else {
                            ++player1Kalah
                            ++currentIndex
                            isPlayer1 = false
                        }
                    } else {
                        if (currentIndex > 0) {
                            --currentIndex
                            ++player2Holes[currentIndex]
                        } else {
                            currentIndex = 0
                            ++player1Holes[currentIndex]
                            isPlayer1 = true
                        }
                    }
                    kalahGameState.value = this.deepCopy()
                    delay(500)
                    if (ind == stones - 1) {
                        isLastInKalah = !isPlayer1 && currentIndex == holesCount
                        isLastInEmpty = try {
                            isPlayer1 && player1Holes[currentIndex] == 1
                        } catch (e: Exception) {
                            false
                        }
                    }
                }
                if (isLastInEmpty) {
                    player1Kalah += player2Holes[currentIndex] + 1
                    player1Holes[currentIndex] = 0
                    player2Holes[currentIndex] = 0
                    kalahGameState.value = this.deepCopy()
                }
                checkForEnding()
                this@GameVsBotViewModel.isMakingMove = false
                currentPlayer.value = if (isLastInKalah) 1 else 2
            }
        } catch (e: Exception) {
            isMakingMove = false
            currentPlayer.value = 1
        }
    }

    private suspend fun makeMovePlayer2(holeIndex: Int) {
        try {
            if (kalahGameState.value!!.player2Holes[holeIndex] == 0) return
            if (isMakingMove) return
            isMakingMove = true
            kalahGameState.value!!.copy().run tmpKalahState@{
                var stones = player2Holes[holeIndex]
                var (currentIndex, isPlayer1) = holeIndex to false
                player2Holes[holeIndex] = 0
                var isLastInKalah = false
                var isLastInEmpty = false
                repeat(stones) { ind ->
                    if (isPlayer1) {
                        if (currentIndex < holesCount - 1) {
                            ++currentIndex
                            ++player1Holes[currentIndex]
                        } else {
                            currentIndex = holesCount - 1
                            ++player2Holes[currentIndex]
                            isPlayer1 = false
                        }
                    } else {
                        if (currentIndex > 0) {
                            --currentIndex
                            ++player2Holes[currentIndex]
                        } else {
                            --currentIndex
                            ++player2Kalah
                            isPlayer1 = true
                        }
                    }
                    kalahGameState.value = this.deepCopy()
                    delay(500)
                    if (ind == stones - 1) {
                        isLastInKalah = isPlayer1 && currentIndex == -1
                        isLastInEmpty = try {
                            !isPlayer1 && player2Holes[currentIndex] == 1
                        } catch (e: Exception) {
                            false
                        }
                    }
                }
                if (isLastInEmpty) {
                    player2Kalah += player1Holes[currentIndex] + 1
                    player1Holes[currentIndex] = 0
                    player2Holes[currentIndex] = 0
                    kalahGameState.value = this.deepCopy()
                }
                checkForEnding()
                this@GameVsBotViewModel.isMakingMove = false
                currentPlayer.value = if (isLastInKalah) 2 else 1
            }
        } catch (e: Exception) {
            isMakingMove = false
            currentPlayer.value = 1
        }
    }

    private suspend fun checkForEnding() {
        kalahGameState.value!!.deepCopy().run tmpKalahState@{
            if (player1Kalah > initialStonesCount * holesCount) {
                currentGameStatus = com.example.kalah_game.model.GameStatus.PLAYER1_WIN
                sendWinEvent()
                kalahGameState.value = this.deepCopy()
                return
            }
            if (player2Kalah > initialStonesCount * holesCount) {
                currentGameStatus = com.example.kalah_game.model.GameStatus.PLAYER2_WIN
                sendWinEvent()
                kalahGameState.value = this.deepCopy()
                return
            }
            if (player1Holes.all { it == 0 } || player2Holes.all { it == 0 }) {
                if (player1Holes.all { it == 0 }) {
                    player2Kalah += player2Holes.sum()
                } else {
                    player1Kalah += player1Holes.sum()
                }

                currentGameStatus = when {
                    player1Kalah > player2Kalah -> com.example.kalah_game.model.GameStatus.PLAYER1_WIN.also{sendWinEvent()}
                    player1Kalah < player2Kalah -> com.example.kalah_game.model.GameStatus.PLAYER2_WIN.also{sendWinEvent()}
                    else -> com.example.kalah_game.model.GameStatus.DRAW
                }
                kalahGameState.value = this.deepCopy()
            }
        }
    }

    private fun sendWinEvent() {
        viewModelScope.launch {
            userLocalRepository.getUser().first()?.let{
                if (it.login != null && it.password != null) {
                    userRemoteRepository.updateUser(
                        it.login!!,
                        it.password!!,
                        it.copy(
                            wins = it.wins.toMutableMap().apply {
                                this[difficulty.value.toString()] =
                                    (this[difficulty.value.toString()] ?: 0) + 1
                            }
                        )
                    )
                }
            }
        }
    }

    fun makeMove(holeIndex: Int) {
        if (currentPlayer.value == 1) {
            viewModelScope.launch {
                makeMovePlayer1(holeIndex)
            }
        }
    }
}