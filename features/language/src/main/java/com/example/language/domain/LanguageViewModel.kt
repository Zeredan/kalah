package com.example.language.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.settings.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    private val _showOkayMark = MutableStateFlow(false)
    val showOkayMark = _showOkayMark.asStateFlow()

    private val _preSelectedLanguageStateFlow = MutableStateFlow<String?>(null)
    val preSelectedLanguageStateFlow = _preSelectedLanguageStateFlow.asStateFlow()


    val selectedLanguageStateFlow = settingsRepository.getSelectedLanguageAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun selectLanguage(){
        viewModelScope.launch {
            preSelectedLanguageStateFlow.value?.let {
                if (preSelectedLanguageStateFlow.value != selectedLanguageStateFlow.value) {
                    _showOkayMark.value = true
                    settingsRepository.updateSelectedLanguage(it)
                }
            }
        }
    }
    fun preSelectLanguage(lang: String) {
        viewModelScope.launch {
            if (lang != preSelectedLanguageStateFlow.value) {
                _showOkayMark.value = true
                _preSelectedLanguageStateFlow.value = lang
            }
        }
    }

    init {
        viewModelScope.launch {
            _preSelectedLanguageStateFlow.value = settingsRepository.getSelectedLanguageAsFlow().first() ?: "en"
        }
    }
}