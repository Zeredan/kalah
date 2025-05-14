package com.example.settings.domain

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networking_core.ktor.di.KtorNetworkSettings
import com.example.settings.model.GameTheme
import com.example.settings.repositories.SettingsRepository
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
): ViewModel() {
    val selectedLanguageStateFlow = settingsRepository.getSelectedLanguageAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
    
    val savedBaseUriStateFlow = settingsRepository.getSavedBaseURIAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val selectedGameTheme = settingsRepository.getGameThemeAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, GameTheme.SAND)

    fun updateDarkMode(value: Boolean){
        viewModelScope.launch {
            settingsRepository.updateDarkModeEnabled(value)
        }
    }
    fun updateSavedBaseUri(uri: String) {
        viewModelScope.launch {
            settingsRepository.updateSavedBaseURI(uri)
        }
    }
    fun updateGameTheme(value: String){
        viewModelScope.launch {
            settingsRepository.updateGameTheme(value)
        }
    }
    fun openGooglePlayForRating(activity: Activity) {
        val manager = ReviewManagerFactory.create(activity)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                manager.launchReviewFlow(activity, reviewInfo)
            } else {
                println("${task.exception} | ${task.isSuccessful} | ${task.isComplete}")
            }
        }
    }

    fun openCustomTab(uri: String, activityContext: Context) {

        val displayMetrics = activityContext.resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val halfHeight = screenHeight / 2

        val intent = CustomTabsIntent.Builder()
            .setInitialActivityHeightPx(
                halfHeight,
                CustomTabsIntent.ACTIVITY_HEIGHT_ADJUSTABLE
            )
            .build()

        val packageName = "com.android.chrome"
        val chromeInstalled = try {
            activityContext.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }

        if (chromeInstalled) {
            intent.intent.setPackage(packageName)
        }
        intent.launchUrl(activityContext, Uri.parse(uri))
    }
}