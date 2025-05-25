package com.example.kalah.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.features.domain.GameVsBotViewModel
import com.example.game_vs_bot.ui.GameVsBotFeatureRoot
import com.example.game_vs_human.ui.GameVsHumanFeatureRoot
import com.example.kalah.ui.theme.AppColorScheme
import com.example.language.ui.LanguageFeatureRoot
import com.example.lobby_management.ui.LobbyManagementFeatureRoot
import com.example.play.ui.PlayFeatureRoot
import com.example.profile.domain.ProfileViewModel
import com.example.profile.ui.ProfileFeatureRoot
import com.example.settings.ui.SettingsFeatureRoot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@SuppressLint("NewApi")
@Composable
fun MainNavigationRoot(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val gameVsBotViewModel: GameVsBotViewModel = hiltViewModel()
    val colorScheme = if (isDarkMode) AppColorScheme.DarkScheme else AppColorScheme.LightScheme

    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->
            NavHost(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(colorResource(colorScheme.backgroundColor)),
                navController = navController,
                startDestination = ScreenState.PLAY,
                enterTransition = {
                    fadeIn(tween(0))
                },
                exitTransition = {
                    fadeOut(tween(0))
                }
            ) {
                composable(ScreenState.GAME_VS_BOT) {
                    GameVsBotFeatureRoot(
                        isDarkMode = isDarkMode,
                        vm = gameVsBotViewModel,
                        onBack = {
                            navController.navigate(ScreenState.PLAY) {
                                popUpTo(ScreenState.GAME_VS_BOT) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable(ScreenState.PLAY) {
                    PlayFeatureRoot(
                        vm = gameVsBotViewModel,
                        isDarkMode = isDarkMode,
                        onNavigateToProfile = {
                            navController.navigate(ScreenState.PROFILE) {
                                popUpTo(ScreenState.PLAY) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateToSettings = {
                            navController.navigate(ScreenState.SETTINGS) {
                                popUpTo(ScreenState.PLAY) {
                                    inclusive = true
                                }
                            }
                        },
                        onPlayWithBot = {
                            navController.navigate(ScreenState.GAME_VS_BOT) {
                                popUpTo(ScreenState.PLAY) {
                                    inclusive = false
                                }
                            }
                        },
                        onPlayWithHuman = {
                            coroutineScope.launch {
                                profileViewModel.myProfileSavedUser.first()?.let {
                                    navController.navigate(ScreenState.LOBBY_MANAGEMENT) {
                                        popUpTo(ScreenState.PLAY) {
                                            inclusive = false
                                        }
                                    }
                                } ?: 0.let{
                                    Toast.makeText(navController.context, "Please sign in first", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    )
                }
                composable(ScreenState.LOBBY_MANAGEMENT) {
                    LobbyManagementFeatureRoot(
                        isDarkMode = isDarkMode,
                        onBack = {
                            navController.navigateUp()
                        },
                        onGameStarted = {
                            navController.navigate("${ScreenState.GAME_VS_HUMAN}/$it") {
                                popUpTo(ScreenState.LOBBY_MANAGEMENT) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable("${ScreenState.GAME_VS_HUMAN}/{gameId}") {
                    val gameId = it.arguments?.getString("gameId")?.toIntOrNull() ?: 0
                    println("gameeeee: gameId: $gameId")

                    GameVsHumanFeatureRoot(
                        isDarkMode = isDarkMode,
                        gameId = gameId,
                        onBack = {
                            navController.navigate(ScreenState.PLAY) {
                                popUpTo(ScreenState.GAME_VS_BOT) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable(ScreenState.PROFILE) {
                    ProfileFeatureRoot(
                        vm = profileViewModel,
                        isDarkMode = isDarkMode,
                        onNavigateToPlay = {
                            navController.navigate(ScreenState.PLAY) {
                                popUpTo(ScreenState.PROFILE) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateToSettings = {
                            navController.navigate(ScreenState.SETTINGS) {
                                popUpTo(ScreenState.PROFILE) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable(ScreenState.SETTINGS) {
                    SettingsFeatureRoot(
                        isDarkMode = isDarkMode,
                        onLangClicked = {
                            navController.navigate(ScreenState.LANGUAGE)
                        },
                        onNavigateToPlay = {
                            navController.navigate(ScreenState.PLAY) {
                                popUpTo(ScreenState.SETTINGS) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateToProfile = {
                            navController.navigate(ScreenState.PROFILE) {
                                popUpTo(ScreenState.SETTINGS) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable(ScreenState.LANGUAGE) {
                    LanguageFeatureRoot(
                        isDarkMode = isDarkMode,
                        onBack = {
                            navController.navigateUp()
                        },
                        onOkay = {
                            navController.navigate(ScreenState.PLAY) {
                                popUpTo(ScreenState.PLAY) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }
    }

}