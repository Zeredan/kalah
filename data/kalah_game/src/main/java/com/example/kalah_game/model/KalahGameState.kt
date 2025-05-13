package com.example.kalah_game.model

import com.example.networking_core.DTOs.KalahGameStateDTO

data class KalahGameState(
    var holesCount: Int,
    var initialStonesCount: Int,
    var player1Holes: MutableList<Int> = MutableList(holesCount) { initialStonesCount },
    var player2Holes: MutableList<Int> = MutableList(holesCount) { initialStonesCount },
    var player1Kalah: Int = 0,
    var player2Kalah: Int = 0,
    var currentGameStatus: GameStatus = GameStatus.PLAYING,
    var currentPlayerInd: Int,
    var player1Nickname: String,
    var player2Nickname: String,
    var isMakingMove: Boolean = false
) {
    constructor(dto: KalahGameStateDTO) : this(
        dto.holesCount,
        dto.initialStonesCount,
        dto.player1Holes.toMutableList(),
        dto.player2Holes.toMutableList(),
        dto.player1Kalah,
        dto.player2Kalah,
        GameStatus.valueOf(dto.currentGameStatus),
        dto.currentPlayerInd,
        dto.player1Nickname,
        dto.player2Nickname,
        dto.isMakingMove
    )
    fun deepCopy() : KalahGameState {
        return KalahGameState(
            holesCount = this.holesCount,
            initialStonesCount = this.initialStonesCount,
            player1Holes = this.player1Holes.toMutableList(),
            player2Holes = this.player2Holes.toMutableList(),
            player1Kalah = this.player1Kalah,
            player2Kalah = this.player2Kalah,
            currentGameStatus = this.currentGameStatus,
            currentPlayerInd = this.currentPlayerInd,
            player1Nickname = this.player1Nickname,
            player2Nickname = this.player2Nickname,
            isMakingMove = this.isMakingMove
        )
    }
}