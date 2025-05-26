package com.example.kalah

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.first
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.Locale
import android.content.res.Configuration
import kotlinx.coroutines.delay
import androidx.lifecycle.lifecycleScope
import com.example.kalah.ui.MainNavigationRoot
import com.example.settings.repositories.SettingsRepository
import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import com.example.kalah.domain.AppViewModel
import com.example.networking_core.ktor.di.KtorNetworkSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var settingsRepository: SettingsRepository

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        lifecycleScope.launch {
            delay(1000)
            newBase?.let { initializeFlows(it) }
            repeat(10) {
                delay(1000)
                changeLanguage(settingsRepository.getSelectedLanguageAsFlow().first() ?: "en")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            settingsRepository.getSavedBaseURIAsFlow().collect{
                KtorNetworkSettings.networkingUrl = it
            }
        }
        setContent {
            val vm: AppViewModel = hiltViewModel()
            val settingsDarkMode by vm.darkModeStateFlow.collectAsState()

            this@MainActivity.window.statusBarColor = when(settingsDarkMode ?: isSystemInDarkTheme()) {
                true -> colorResource(R.color.black1).toArgb()
                false -> colorResource(R.color.white1).toArgb()
            }
            this@MainActivity.window.navigationBarColor = when(settingsDarkMode ?: isSystemInDarkTheme()) {
                true -> colorResource(R.color.black1).toArgb()
                false -> colorResource(R.color.white1).toArgb()
            }
            MainNavigationRoot(
                isDarkMode = settingsDarkMode ?: isSystemInDarkTheme()
            )
        }
    }

    fun changeLanguage(
        language: String
    ) {
        println("language select required -> $language")
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)

        // Create new context with the updated configuration and apply it
        resources.updateConfiguration(config, resources.displayMetrics)

        // Check if we need to recreate
        val currentLocale = resources.configuration.locales[0]
        if (currentLocale.language != language) {
            //this@MainActivity.recreate()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun initializeFlows(context: Context) {
        lifecycleScope.launch {
            settingsRepository.getSelectedLanguageAsFlow().collect { language ->
                if (language != null) {
                    //changeLanguage("en", context)
                    changeLanguage(language)
                }
            }
        }
        lifecycleScope.launch {
            settingsRepository.getDarkModeEnabledAsFlow().first() ?: let{
                val darkMode = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
                settingsRepository.updateDarkModeEnabled(darkMode)
            }
        }
    }
}
