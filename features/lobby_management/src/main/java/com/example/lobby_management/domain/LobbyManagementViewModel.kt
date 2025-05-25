package com.example.lobby_management.domain

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lobby.model.Lobby
import com.example.lobby.repositories.LobbyRemoteRepository
import com.example.user.repositories.UserLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LobbyManagementViewModel @Inject constructor(
    private val lobbyRepository: LobbyRemoteRepository,
    private val userLocalRepository: UserLocalRepository
) : ViewModel() {
    private var isInitialized = false
    lateinit var onGameStarted: (gameId: Int) -> Unit

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isCreatorMode = MutableStateFlow<Boolean?>(null)
    val isCreatorMode = _isCreatorMode.asStateFlow()
    
    val availableLobbies = mutableStateListOf<Lobby>()

    private val _selectedLobby = MutableStateFlow<Lobby?>(null)
    val selectedLobby = _selectedLobby.asStateFlow()
    
    private val _inputLobbyName = MutableStateFlow("")
    val inputLobbyName = _inputLobbyName.asStateFlow()
    
    private val _inputStonesCountStr = MutableStateFlow("3")
    val inputStonesCountStr = _inputStonesCountStr.asStateFlow()
    val inputStonesCount = inputStonesCountStr.map{
        it.toIntOrNull() ?: 3
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 3)
    
    private val _inputHolesCountStr = MutableStateFlow("6")
    val inputHolesCountStr = _inputHolesCountStr.asStateFlow()
    val inputHolesCount = inputHolesCountStr.map{
        it.toIntOrNull() ?: 6
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 6)
    
    fun updateHolesCountStr(holesStr: String) {
        _inputHolesCountStr.value = holesStr
    }
    
    fun updateLobbyName(name: String) {
        _inputLobbyName.value = name
    }
    
    fun updateStonesCountStr(stonesStr: String) {
        _inputStonesCountStr.value = stonesStr
    }

    fun reloadLobbies() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val lobbies = lobbyRepository.getAllLobbies()
            availableLobbies.clear()
            availableLobbies += lobbies
            _isLoading.value = false
        }
    }

    fun joinLobby(lobbyId: Int) : Deferred<Boolean> {
        return viewModelScope.async(Dispatchers.IO) {
            try {
                val lobby = lobbyRepository.joinLobby(
                    lobbyId = lobbyId,
                    user = userLocalRepository.getUser().first()!!
                )
                if (lobby != null) {
                    _isCreatorMode.value = false
                    viewModelScope.launch {
                        lobbyRepository.trackLobby(lobbyId = lobby.id).collect {
                            _selectedLobby.value = it
                        }
                    }
                    viewModelScope.launch {
                        lobbyRepository.trackGameStart(lobbyId = lobby.id).collect {
                            if (it) onGameStarted(lobby.id)
                        }
                    }
                    true
                } else false
            } catch (e: Exception) {
                false
            }
        }
    }

    fun createLobby() : Deferred<Lobby?> {
        return viewModelScope.async(Dispatchers.IO) {
            try {
                val stonesCount = inputStonesCount.value
                val holesCount = inputHolesCount.value
                val lobby = lobbyRepository.createLobby(
                    Lobby(
                        id = 0,
                        name = _inputLobbyName.value,
                        initialStones = stonesCount,
                        initialHoles = holesCount,
                        creator = userLocalRepository.getUser().first()!!,
                        guest = null
                    )
                )
                if (lobby != null) {
                    _isCreatorMode.value = true
                    viewModelScope.launch {
                        lobbyRepository.trackLobby(lobbyId = lobby.id).collect {
                            _selectedLobby.value = it
                        }
                    }
                    viewModelScope.launch {
                        lobbyRepository.trackGameStart(lobbyId = lobby.id).collect {
                            println("FFFGD: ${lobby.id}")
                            if (it) onGameStarted(lobby.id)
                        }
                    }
                }
                lobby
            } catch (e: Exception) {
                null
            }
        }
    }

    fun startGame() {
        viewModelScope.launch(Dispatchers.IO) {
            selectedLobby.value?.let {
                lobbyRepository.startGame(it.id)
            }
        }
    }



    fun initialize(){
        if (isInitialized) return
        isInitialized = true
        reloadLobbies()
    }
}