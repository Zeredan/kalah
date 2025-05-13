package com.example.profile.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.user.model.User
import com.example.user.repositories.UserLocalRepository
import com.example.user.repositories.UserRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRemoteRepository: UserRemoteRepository,
    private val userLocalRepository: UserLocalRepository
): ViewModel() {
    var isLoading by mutableStateOf(true)
    private val _registerLogin = MutableStateFlow("")
    val registerLogin = _registerLogin.asStateFlow()

    private val _registerPassword = MutableStateFlow("")
    val registerPassword = _registerPassword.asStateFlow()

    val myProfileSavedUser = userLocalRepository.getUser()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            null
        )

    fun register() {
        viewModelScope.launch {
            if (registerLogin.value.isNotBlank() && registerPassword.value.isNotBlank()) {
                isLoading = true
                try {
                    userRemoteRepository.getUserByCredentials(
                        registerLogin.value,
                        registerPassword.value
                    )?.let {
                        userLocalRepository.saveUser(it)
                    } ?: let {
                        userRemoteRepository.createUser(registerLogin.value, registerPassword.value)
                        val createdUser = userRemoteRepository.getUserByCredentials(
                            registerLogin.value,
                            registerPassword.value
                        )
                        userLocalRepository.saveUser(createdUser)
                    }
                } catch (e: Exception) {

                }
                isLoading = false
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            userLocalRepository.saveUser(null)
        }
    }

    fun updateRegisterLogin(value: String) {
        _registerLogin.value = value
    }

    fun updateRegisterPassword(value: String) {
        _registerPassword.value = value
    }


    init {
        viewModelScope.launch {
            userLocalRepository.getUser().first()?.let{
                if (it.login != null && it.password != null) {
                    try {
                        val newUser = userRemoteRepository.getUserByCredentials(it.login!!, it.password!!)
                        userLocalRepository.saveUser(newUser)
                    } catch (e: Exception) {

                    }
                }
                isLoading = false
            } ?: let{
                isLoading = false
            }
        }
    }
}