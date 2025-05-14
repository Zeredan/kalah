package com.example.game_vs_human.ui

import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel

import com.example.game_vs_human.ui.theme.ColorScheme
import com.example.game_vs_human.ui.theme.IconScheme
import com.example.features.R
import com.example.game_vs_human.domain.GameVsHumanViewModel
import com.example.kalah_game.model.GameStatus
import com.example.settings.model.GameTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameVsHumanFeatureRoot(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    vm: GameVsHumanViewModel = hiltViewModel(),
    gameId: Int,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    DisposableEffect (1) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            //vm.stopGame()
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
    LaunchedEffect(1) {
        vm.initialize(gameId)
    }
    val colorScheme = if (isDarkMode) ColorScheme.DarkScheme else ColorScheme.LightScheme
    val iconScheme = if (isDarkMode) IconScheme.DarkScheme else IconScheme.LightScheme

    BackHandler {
        onBack()
    }
    val gameTheme by vm.selectedGameTheme.collectAsState()
    val kalahGameStateTrue by vm.kalahGameState.collectAsState()
    val currentUserMe by vm.currentUserMe.collectAsState()
    
    val kalahGameState = kalahGameStateTrue?.let { gameState ->
        if (currentUserMe?.nickname == gameState.player1Nickname) {
            gameState
        } else {
            gameState.copy(
                player1Nickname = gameState.player2Nickname,
                player2Nickname = gameState.player1Nickname,
                player1Kalah = gameState.player2Kalah,
                player2Kalah = gameState.player1Kalah,
                player1Holes = gameState.player2Holes,
                player2Holes = gameState.player1Holes,
                currentPlayerInd = if (gameState.currentPlayerInd == 1) 2 else 1,
                currentGameStatus = when (gameState.currentGameStatus) {
                    GameStatus.PLAYER1_WIN -> GameStatus.PLAYER2_WIN
                    GameStatus.PLAYER2_WIN -> GameStatus.PLAYER1_WIN
                    else -> gameState.currentGameStatus
                }
            )
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(colorScheme.backgroundColor)),
        contentAlignment = Alignment.Center
    ) {
        gameTheme?.let {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(
                    when (it) {
                        GameTheme.SAND -> R.drawable.sand_background
                        GameTheme.PIRATE -> R.drawable.pirate_background
                        GameTheme.STARS -> R.drawable.stars_background
                        GameTheme.KNIGHTS -> R.drawable.knight_background
                        GameTheme.VIKINGS -> R.drawable.vikings_background
                        else -> R.drawable.pirate_background
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(11.dp, 0.dp, 11.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = modifier
                        .padding(end = 7.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            onBack()
                        }
                        .padding(5.dp),
                    painter = painterResource(iconScheme.backIcon),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )
                Text(
                    text = stringResource(com.example.features.R.string.kalah),
                    fontSize = 22.sp,
                    color = colorResource(colorScheme.textColor),
                    fontWeight = FontWeight.W600
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = kalahGameState?.player2Nickname ?: "???",
                    fontSize = 22.sp,
                    color = colorResource(colorScheme.textColor),
                    fontWeight = FontWeight.W600
                )
            }
            if (kalahGameState?.currentGameStatus != null && kalahGameState?.currentGameStatus != GameStatus.PLAYING) {
                Dialog(
                    onDismissRequest = {

                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.8f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorResource(colorScheme.backgroundColor))
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(
                                when (kalahGameState?.currentGameStatus) {
                                    com.example.kalah_game.model.GameStatus.PLAYER1_WIN -> R.string.victory
                                    com.example.kalah_game.model.GameStatus.PLAYER2_WIN -> R.string.defeat
                                    else -> R.string.draw
                                }
                            ),
                            fontSize = 22.sp,
                            color = colorResource(colorScheme.textColor),
                            fontWeight = FontWeight.W600
                        )
                        Spacer(Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .size(100.dp, 56.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(colorResource(R.color.green))
                                .clickable {
                                    onBack()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.back),
                                fontSize = 22.sp,
                                color = colorResource(colorScheme.textColor),
                                fontWeight = FontWeight.W600
                            )
                        }
                    }
                }
            }
            Row(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(0.5f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(colorResource(colorScheme.holeBackgroundColor)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = kalahGameState?.player2Kalah?.toString() ?: "?",
                        fontSize = 22.sp,
                        color = colorResource(colorScheme.textColor),
                        fontWeight = FontWeight.W600
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(6f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        kalahGameState?.player2Holes?.forEach {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(colorResource(colorScheme.holeBackgroundColor))
                                    .run {
                                        if (kalahGameState.currentPlayerInd == 2 && !kalahGameState.isMakingMove && it > 0) {
                                            this.border(
                                                2.dp,
                                                color = colorResource(R.color.red),
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                        } else this
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = it.toString(),
                                    fontSize = 22.sp,
                                    color = colorResource(colorScheme.textColor),
                                    fontWeight = FontWeight.W600
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        kalahGameState?.player1Holes?.forEachIndexed { ind, it ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(colorResource(colorScheme.holeBackgroundColor))
                                    .run {
                                        if (kalahGameState.currentPlayerInd == 1 && !kalahGameState.isMakingMove && it > 0) {
                                            this.border(
                                                2.dp,
                                                color = colorResource(R.color.green),
                                                shape = RoundedCornerShape(16.dp)
                                            ).clickable {
                                                vm.makeMove(ind)
                                            }
                                        } else this
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = it.toString(),
                                    fontSize = 22.sp,
                                    color = colorResource(colorScheme.textColor),
                                    fontWeight = FontWeight.W600
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(0.5f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(colorResource(colorScheme.holeBackgroundColor)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = kalahGameState?.player1Kalah?.toString() ?: "?",
                        fontSize = 22.sp,
                        color = colorResource(colorScheme.textColor),
                        fontWeight = FontWeight.W600
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(11.dp, 0.dp, 11.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Me",
                    fontSize = 22.sp,
                    color = colorResource(colorScheme.textColor),
                    fontWeight = FontWeight.W600
                )
            }
        }
    }
}