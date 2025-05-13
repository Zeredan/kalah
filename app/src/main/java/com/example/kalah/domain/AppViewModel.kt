package com.example.kalah.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.settings.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
): ViewModel() {
    val darkModeStateFlow = settingsRepository.getDarkModeEnabledAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val onboardingStateFlow = settingsRepository.getOnboardingShowedAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun updateOnboardingState(isShowed: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.updateOnboardingShowed(isShowed)
        }
    }
}