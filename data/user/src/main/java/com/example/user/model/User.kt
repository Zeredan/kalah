package com.example.user.model

import com.example.networking_core.DTOs.UserDTO

data class User(
    val nickname: String,
    val wins: Map<String, Int>,
    val login: String?,
    val password: String?
) {
    fun toDTO(): UserDTO {
        return UserDTO(nickname, wins, login, password)
    }

    constructor(userDTO: UserDTO) : this(userDTO.nickname, userDTO.wins, userDTO.login, userDTO.password)
}
