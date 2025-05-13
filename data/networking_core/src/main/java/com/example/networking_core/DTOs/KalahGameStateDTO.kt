package com.example.networking_core.DTOs

data class KalahGameStateDTO(
    var holesCount: Int,
    var initialStonesCount: Int,
    var player1Holes: List<Int>,
    var player2Holes: List<Int>,
    var player1Kalah: Int,
    var player2Kalah: Int,
    var currentGameStatus: String, // Using String instead of the enum for network communication,
    var currentPlayerInd: Int,
    var player1Nickname: String,
    var player2Nickname: String,
    var isMakingMove: Boolean = false
)