package com.example.features.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.features.model.BotGameDifficulty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PlayViewModel @Inject constructor(
): ViewModel() {
    protected val _difficulty = MutableStateFlow(BotGameDifficulty.Easy)
    val difficulty = _difficulty.asStateFlow()

    protected val _stoneCount = MutableStateFlow(3)
    val stoneCount = _stoneCount.asStateFlow()

    protected val _holesCount = MutableStateFlow(5)
    val holesCount = _holesCount.asStateFlow()

    fun updateDifficulty(newDifficulty: BotGameDifficulty) {
        viewModelScope.launch {
            _difficulty.emit(newDifficulty)
        }
    }
    fun updateHolesCount(newCount: Int) {
        viewModelScope.launch {
            if (newCount in 3..8) {
                _holesCount.emit(newCount)
            }
        }
    }

    fun updateStoneCount(newCount: Int) {
        viewModelScope.launch {
            if (newCount in 3..10) {
                _stoneCount.emit(newCount)
            }
        }
    }

}