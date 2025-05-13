package com.example.settings.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.settings.model.GameTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {
    private val LANG_KEY = stringPreferencesKey("language")
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val ONBOARDING = booleanPreferencesKey("onboarding")
    private val GAME_THEME_KEY = stringPreferencesKey("game_theme")

    override fun getSelectedLanguageAsFlow(): Flow<String?> {
        return dataStore.data.map {
            it[LANG_KEY]
        }
    }

    override suspend fun updateSelectedLanguage(lang: String) {
        dataStore.edit {
            it[LANG_KEY] = lang
        }
    }

    override fun getDarkModeEnabledAsFlow(): Flow<Boolean?> {
        return dataStore.data.map{
            it[DARK_MODE_KEY]
        }
    }

    override suspend fun updateDarkModeEnabled(isEnabled: Boolean) {
        dataStore.edit {
            it[DARK_MODE_KEY] = isEnabled
        }
    }

    override fun getOnboardingShowedAsFlow(): Flow<Boolean> {
        return dataStore.data.map{
            it[ONBOARDING] ?: false
        }
    }

    override suspend fun updateOnboardingShowed(isShowed: Boolean) {
        dataStore.edit {
            it[ONBOARDING] = isShowed
        }
    }

    override fun getGameThemeAsFlow(): Flow<String> {
        return dataStore.data.map{
            it[GAME_THEME_KEY] ?: GameTheme.SAND
        }
    }

    override suspend fun updateGameTheme(theme: String) {
        dataStore.edit {
            it[GAME_THEME_KEY] = theme
        }
    }
}