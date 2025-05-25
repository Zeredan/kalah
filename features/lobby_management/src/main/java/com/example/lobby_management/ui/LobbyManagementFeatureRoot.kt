package com.example.lobby_management.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lobby_management.domain.LobbyManagementViewModel
import com.example.lobby_management.ui.theme.ColorScheme
import com.example.lobby_management.ui.theme.IconScheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LobbyManagementFeatureRoot(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    vm: LobbyManagementViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onGameStarted: (gameId: Int) -> Unit
) {
    val colorScheme = if (isDarkMode) ColorScheme.DarkScheme else ColorScheme.LightScheme
    val iconScheme = if (isDarkMode) IconScheme.DarkScheme else IconScheme.LightScheme
    val context = LocalContext.current

    val navController = rememberNavController()

    LaunchedEffect(1) {
        vm.onGameStarted = {
            onGameStarted(it)
        }
        vm.initialize()
    }

    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(colorScheme.backgroundColor)),
        navController = navController,
        startDestination = "lobby_observing",
    ) {
        composable("lobby_observing") {
            LobbyObservingScreen(
                onCreateLobby = { navController.navigate("lobby_creation") },
                onJoinLobby = {
                    navController.navigate("lobby")
                },
                isDarkMode = isDarkMode,
                vm = vm,
                onBack = {
                    onBack()
                }
            )
        }

        composable("lobby_creation") {
            LobbyCreationScreen(
                onLobbyCreated = {
                    navController.navigate("lobby"){
                        popUpTo("lobby_observing"){
                            inclusive = false
                        }
                    }
                },
                onBack = { navController.navigateUp() },
                isDarkMode = isDarkMode,
                vm = vm
            )
        }

        composable(
            route = "lobby",
        ) {
            LobbyScreen(
                onLeave = { navController.navigateUp() },
                isDarkMode = isDarkMode,
                vm = vm,
                onGameStarted = onGameStarted
            )
        }
    }
}