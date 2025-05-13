package com.example.networking_core.DTOs

data class UserDTO(
    val nickname: String,
    val wins: Map<String, Int>,
    val login: String?,
    val password: String?
)
