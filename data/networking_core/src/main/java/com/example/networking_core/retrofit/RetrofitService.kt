package com.example.networking_core.retrofit

import com.example.networking_core.DTOs.UserDTO
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface RetrofitService {
    @GET("users")
    suspend fun getUserByCredentials(@Query("login") login: String, @Query("password") password: String) : UserDTO

    @GET("users")
    suspend fun getAllUsers() : List<UserDTO>

    @GET("users")
    suspend fun findUsersByName(@Query("filter") filter: String) : List<UserDTO>

    @PATCH("users")
    suspend fun updateUser(@Query("login") login: String, @Query("password") password: String, newUser: UserDTO)
}