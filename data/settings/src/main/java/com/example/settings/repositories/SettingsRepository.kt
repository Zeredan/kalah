package com.example.settings.repositories

import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SettingsRepository {
    fun getSelectedLanguageAsFlow() : Flow<String?>
    suspend fun updateSelectedLanguage(lang: String)

    fun getDarkModeEnabledAsFlow() : Flow<Boolean?>
    suspend fun updateDarkModeEnabled(isEnabled: Boolean)

    fun getOnboardingShowedAsFlow() : Flow<Boolean>
    suspend fun updateOnboardingShowed(isShowed: Boolean)

    fun getGameThemeAsFlow(): Flow<String>
    suspend fun updateGameTheme(theme: String)
}