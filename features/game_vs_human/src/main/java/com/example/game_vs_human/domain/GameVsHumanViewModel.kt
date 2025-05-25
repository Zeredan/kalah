package com.example.game_vs_human.domain

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kalah_game.model.KalahGameState
import com.example.kalah_game.repositories.KalahGameRemoteRepository
import com.example.settings.repositories.SettingsRepository
import com.example.user.repositories.UserLocalRepository
import com.example.user.repositories.UserRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameVsHumanViewModel @Inject constructor(
    private val userRemoteRepository: UserRemoteRepository,
    private val userLocalRepository: UserLocalRepository,
    private val kalahGameRemoteRepository: KalahGameRemoteRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    val selectedGameTheme = settingsRepository.getGameThemeAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private var gameId by mutableStateOf(0)
    private var isInitialized = false
    val kalahGameState = MutableStateFlow<KalahGameState?>(null)
    
    val currentUserMe = userLocalRepository.getUser().stateIn(
        viewModelScope, SharingStarted.Eagerly, null
    )

    fun makeMove(holeIndex: Int) {
        viewModelScope.launch {
            kalahGameRemoteRepository.makeMove(gameId, currentUserMe.value!!.nickname, holeIndex)
        }
    }

    fun initialize(gameId: Int) {
        this.gameId = gameId
        if (isInitialized) return
        isInitialized = true
        viewModelScope.launch {
            kalahGameRemoteRepository.trackGame(gameId).collect{
                kalahGameState.value = it
            }
        }
    }
}