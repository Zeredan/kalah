package com.example.networking_core.ktor

import android.content.Context
import android.widget.Toast
import com.example.networking_core.DTOs.LobbyDTO
import com.example.networking_core.DTOs.UserDTO
import com.example.networking_core.DTOs.KalahGameStateDTO
import com.example.networking_core.ktor.di.KtorNetworkSettings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flowOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import kotlin.time.Duration

class KtorService @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val client: HttpClient
) {
    val gson = Gson()
    suspend fun getUserByCredentials(login: String, password: String) : UserDTO? {
        Toast.makeText(appContext, "NETWORK: Getting user by credentials - login: $login", Toast.LENGTH_SHORT).show()
        println("NETWORK: Getting user by credentials - login: $login")
        return try {
            client.request(
                urlString = "${KtorNetworkSettings.networkingUrl}/users"
            ) {
                method = HttpMethod.Get
                parameter("login", login)
                parameter("password", password)
            }.bodyAsText().let{gson.fromJson(it, UserDTO::class.java)}
        } catch (e: Exception) {
            println("NETWORK: Error getting user by credentials - ${e.message}")
            null
        }
    }

    suspend fun getAllUsers() : List<UserDTO> {
        Toast.makeText(appContext, "NETWORK: Getting all users", Toast.LENGTH_SHORT).show()
        println("NETWORK: Getting all users")
        return try {
            client.request(
                urlString = "${KtorNetworkSettings.networkingUrl}/users"
            ) {
                method = HttpMethod.Get
            }.bodyAsText().let{gson.fromJson(it, object : TypeToken<List<UserDTO>>(){}.type)}
        } catch (e: Exception) {
            println("NETWORK: Error getting all users - ${e.message}")
            emptyList()
        }
    }

    suspend fun filterUsersByName(filter: String) : List<UserDTO> {
        println("NETWORK: Filtering users by name: $filter")
        return try {
            client.request(
                urlString = "${KtorNetworkSettings.networkingUrl}/users"
            ) {
                method = HttpMethod.Get
                parameter("filter", filter)
            }.bodyAsText().let{gson.fromJson(it, object : TypeToken<List<UserDTO>>(){}.type)}
        } catch (e: Exception) {
            println("NETWORK: Error filtering users - ${e.message}")
            emptyList()
        }
    }

    suspend fun createUser(login: String, password: String): Boolean {
        Toast.makeText(appContext, "NETWORK: Creating user - login: $login", Toast.LENGTH_SHORT).show()
        println("NETWORK: Creating user - login: $login")
        return try {
            client.request(
                urlString = "${KtorNetworkSettings.networkingUrl}/users"
            ) {
                method = HttpMethod.Post
                parameter("login", login)
                parameter("password", password)
            }
            println("NETWORK: User created successfully")
            true
        } catch (e: Exception) {
            println("NETWORK: Error creating user - ${e.message}")
            false
        }
    }

    suspend fun updateUser(login: String, password: String, newUser: UserDTO): Boolean {
        Toast.makeText(appContext, "NETWORK: Updating user - login: $login, newUser: $newUser", Toast.LENGTH_SHORT).show()
        println("NETWORK: Updating user - login: $login, newUser: $newUser")
        return try {
            client.request(
                urlString = "${KtorNetworkSettings.networkingUrl}/users"
            ) {
                method = HttpMethod.Put
                parameter("login", login)
                parameter("password", password)
                setBody(newUser)
            }
            println("NETWORK: User updated successfully")
            true
        } catch (e: Exception) {
            println("NETWORK: Error updating user - ${e.message}")
            false
        }
    }
    
    suspend fun getAllLobbies() : List<LobbyDTO> {
        Toast.makeText(appContext, "NETWORK: Getting all lobbies", Toast.LENGTH_SHORT).show()
        println("NETWORK: Getting all lobbies")
        return try {
            client.request(
                urlString = "${KtorNetworkSettings.networkingUrl}/lobbies"
            ) {
                method = HttpMethod.Get
            }.bodyAsText().let{gson.fromJson(it, object : TypeToken<List<LobbyDTO>>(){}.type)}
        } catch (e: Exception) {
            println("NETWORK: Error getting all lobbies - ${e.message}")
            emptyList()
        }
    }

    suspend fun createLobby(lobbyDTO: LobbyDTO): LobbyDTO? {
        Toast.makeText(appContext, "NETWORK: Creating lobby: $lobbyDTO", Toast.LENGTH_SHORT).show()
        println("NETWORK: Creating lobby: $lobbyDTO")
        return try {
            client.request(
                urlString = "${KtorNetworkSettings.networkingUrl}/lobbies/create"
            ) {
                method = HttpMethod.Post
                setBody(lobbyDTO)
            }.bodyAsText().let{gson.fromJson(it, LobbyDTO::class.java)}
        } catch (e: Exception) {
            println("NETWORK: Error creating lobby - ${e.message}")
            null
        }
    }

    suspend fun joinLobby(lobbyId: Int, userDTO: UserDTO): LobbyDTO? {
        Toast.makeText(appContext, "NETWORK: Joining lobby id: $lobbyId with user: $userDTO", Toast.LENGTH_SHORT).show()
        println("NETWORK: Joining lobby id: $lobbyId with user: $userDTO")
        return try {
            client.request(
                urlString = "${KtorNetworkSettings.networkingUrl}/lobbies/$lobbyId/join"
            ) {
                method = HttpMethod.Post
                setBody(userDTO)
            }.bodyAsText().let{gson.fromJson(it, LobbyDTO::class.java)}
        } catch (e: Exception) {
            println("NETWORK: Error joining lobby - ${e.message}")
            null
        }
    }

    suspend fun trackLobby(lobbyId: Int): Flow<LobbyDTO> {
        Toast.makeText(appContext, "NETWORK: Starting to track lobby id: $lobbyId", Toast.LENGTH_SHORT).show()
        println("NETWORK: Starting to track lobby id: $lobbyId")
        return try {
            val session: WebSocketSession = client.webSocketSession(
                urlString = "${KtorNetworkSettings.networkingUrl}/lobbies/$lobbyId/track"
            )
            
            session.incoming.consumeAsFlow().map { frame ->
                when (frame) {
                    is Frame.Text -> {
                        val text = frame.readText()
                        println("NETWORK: Received lobby update: $text")
                        Gson().fromJson(text, LobbyDTO::class.java)
                    }
                    else -> throw IllegalStateException("Unexpected frame type")
                }
            }
        } catch (e: Exception) {
            println("NETWORK: Error tracking lobby - ${e.message}")
            flowOf()
        }
    }
    
    suspend fun startGame(lobbyId: Int): Boolean {
        Toast.makeText(appContext, "NETWORK: Starting game for lobby id: $lobbyId", Toast.LENGTH_SHORT).show()
        println("NETWORK: Starting game for lobby id: $lobbyId")
        return try {
            client.request(
                urlString = "${KtorNetworkSettings.networkingUrl}/games/$lobbyId/start"
            ) {
                method = HttpMethod.Post
            }
            println("NETWORK: Game started successfully")
            true
        } catch (e: Exception) {
            println("NETWORK: Error starting game - ${e.message}")
            false
        }
    }

    suspend fun trackGame(lobbyId: Int): Flow<KalahGameStateDTO> {
        Toast.makeText(appContext, "NETWORK: Starting to track game for lobby id: $lobbyId", Toast.LENGTH_SHORT).show()
        println("NETWORK: Starting to track game for lobby id: $lobbyId")
        return try {
            val session: WebSocketSession = client.webSocketSession(
                urlString = "${KtorNetworkSettings.networkingUrl}/games/$lobbyId/track"
            )
            
            session.incoming.consumeAsFlow().map { frame ->
                when (frame) {
                    is Frame.Text -> {
                        val text = frame.readText()
                        println("NETWORK: Received game state update: $text")
                        Gson().fromJson(text, KalahGameStateDTO::class.java)
                    }
                    else -> throw IllegalStateException("Unexpected frame type")
                }
            }
        } catch (e: Exception) {
            println("NETWORK: Error tracking game - ${e.message}")
            flowOf()
        }
    }
    
    suspend fun makeMove(lobbyId: Int, nickname: String, holeIndex: Int): Boolean {
        Toast.makeText(appContext, "NETWORK: Making move - lobby: $lobbyId, user: $nickname, hole: $holeIndex", Toast.LENGTH_SHORT).show()
        println("NETWORK: Making move - lobby: $lobbyId, user: $nickname, hole: $holeIndex")
        return try {
            client.request(
                urlString = "${KtorNetworkSettings.networkingUrl}/games/$lobbyId/move"
            ) {
                method = HttpMethod.Post
                parameter("nickname", nickname)
                parameter("holeIndex", holeIndex.toString())
            }
            println("NETWORK: Move made successfully")
            true
        } catch (e: Exception) {
            println("NETWORK: Error making move - ${e.message}")
            false
        }
    }
}