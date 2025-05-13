package com.example.lobby.model

import com.example.networking_core.DTOs.LobbyDTO
import com.example.networking_core.DTOs.UserDTO
import com.example.user.model.User

data class Lobby(
    val id: Int,
    val name: String,
    val initialStones: Int,
    val initialHoles: Int,
    val creator: User,
    val guest: User?
) {
    fun toDTO(): LobbyDTO {
        return LobbyDTO(id, name, initialStones, initialHoles, creator.toDTO(), guest?.toDTO())
    }

    constructor(lobbyDTO: LobbyDTO) : this(
        lobbyDTO.id,
        lobbyDTO.name,
        lobbyDTO.initialStones,
        lobbyDTO.initialHoles,
        User(lobbyDTO.creator),
        lobbyDTO.guest?.let { User(it) }
    )
}
