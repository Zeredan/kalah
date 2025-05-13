package com.example.networking_core.DTOs

data class LobbyDTO(
    val id: Int,
    val name: String,
    val initialStones: Int,
    val initialHoles: Int,
    val creator: UserDTO,
    val guest: UserDTO?
)
