package com.example.play.ui

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.features.model.MainNavigationPath
import com.example.features.ui.MainPathsNavigationRow
import com.example.features.domain.PlayViewModel
import com.example.features.model.BotGameDifficulty
import com.example.play.ui.theme.ColorScheme
import com.example.play.ui.theme.IconScheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayFeatureRoot(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    vm: PlayViewModel,
    onNavigateToProfile: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onPlayWithBot: () -> Unit,
    onPlayWithHuman: () -> Unit,
) {
    val colorScheme = if (isDarkMode) ColorScheme.DarkScheme else ColorScheme.LightScheme
    val iconScheme = if (isDarkMode) IconScheme.DarkScheme else IconScheme.LightScheme

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .background(colorResource(colorScheme.backgroundColor)),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(com.example.features.R.string.kalah),
                    fontSize = 22.sp,
                    color = colorResource(colorScheme.textColor),
                    fontWeight = FontWeight.W600,
                )
                Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(100.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(colorScheme.playBackgroundColor))
                    .clickable { onPlayWithHuman() }
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(com.example.features.R.drawable.play),
                    contentDescription = null,
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(com.example.features.R.string.play_vs_human),
                    fontSize = 18.sp,
                    color = colorResource(com.example.features.R.color.white),
                    fontWeight = FontWeight.W500,
                )
                Spacer(Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(58.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(2f)
                    .border(
                        width = 3.dp,
                        color = colorResource(colorScheme.playBackgroundColor),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val difficulty by vm.difficulty.collectAsState()
                val stoneCount by vm.stoneCount.collectAsState()
                val holesCount by vm.holesCount.collectAsState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(colorResource(colorScheme.playBackgroundColor))
                        .clickable {
                            onPlayWithBot()
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(48.dp),
                        painter = painterResource(com.example.features.R.drawable.play),
                        contentDescription = null,
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = stringResource(com.example.features.R.string.play_vs_bot),
                        fontSize = 18.sp,
                        color = colorResource(com.example.features.R.color.white),
                        fontWeight = FontWeight.W500,
                    )
                    Spacer(Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(colorResource(colorScheme.gameOptionBackgroundColor))
                        .clickable {
                            vm.updateDifficulty(
                                when(difficulty) {
                                    BotGameDifficulty.Easy -> BotGameDifficulty.Medium
                                    BotGameDifficulty.Medium -> BotGameDifficulty.Hard
                                    BotGameDifficulty.Hard -> BotGameDifficulty.Easy
                                }
                            )
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(100.dp),
                        text = stringResource(com.example.features.R.string.difficulty) + ": ",
                        fontSize = 16.sp,
                        color = colorResource(com.example.features.R.color.white),
                        fontWeight = FontWeight.W500,
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .weight(1f),
                            painter = painterResource(
                                when(difficulty) {
                                    BotGameDifficulty.Easy -> com.example.features.R.drawable.easy
                                    BotGameDifficulty.Medium -> com.example.features.R.drawable.medium
                                    BotGameDifficulty.Hard -> com.example.features.R.drawable.hard
                                }
                            ),
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(
                                when(difficulty) {
                                    BotGameDifficulty.Easy -> com.example.features.R.string.easy
                                    BotGameDifficulty.Medium -> com.example.features.R.string.medium
                                    BotGameDifficulty.Hard -> com.example.features.R.string.hard
                                }
                            ),
                            fontSize = 16.sp,
                            color = colorResource(com.example.features.R.color.orange),
                            fontWeight = FontWeight.W500,
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(colorResource(colorScheme.gameOptionBackgroundColor))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(100.dp),
                        text = stringResource(com.example.features.R.string.stones) + ": ",
                        fontSize = 16.sp,
                        color = colorResource(com.example.features.R.color.white),
                        fontWeight = FontWeight.W500,
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .weight(1f),
                            painter = painterResource(com.example.features.R.drawable.stones),
                            contentDescription = null
                        )
                        Text(
                            text = stoneCount.toString(),
                            fontSize = 16.sp,
                            color = colorResource(com.example.features.R.color.orange),
                            fontWeight = FontWeight.W400,
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(5.dp))
                            .clickable {
                                vm.updateStoneCount(stoneCount - 1)
                            }
                            .background(colorResource(com.example.features.R.color.orange))
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "-",
                            fontSize = 22.sp,
                            color = colorResource(com.example.features.R.color.purple_700),
                            fontWeight = FontWeight.W500,
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(5.dp))
                            .clickable {
                                vm.updateStoneCount(stoneCount + 1)
                            }
                            .background(colorResource(com.example.features.R.color.orange))
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+",
                            fontSize = 22.sp,
                            color = colorResource(com.example.features.R.color.purple_700),
                            fontWeight = FontWeight.W500,
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(colorResource(colorScheme.gameOptionBackgroundColor))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(100.dp),
                        text = stringResource(com.example.features.R.string.holes) + ": ",
                        fontSize = 16.sp,
                        color = colorResource(com.example.features.R.color.white),
                        fontWeight = FontWeight.W500,
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .weight(1f),
                            painter = painterResource(com.example.features.R.drawable.hole),
                            contentDescription = null
                        )
                        Text(
                            text = holesCount.toString(),
                            fontSize = 16.sp,
                            color = colorResource(com.example.features.R.color.orange),
                            fontWeight = FontWeight.W400,
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(5.dp))
                            .clickable {
                                vm.updateHolesCount(holesCount - 1)
                            }
                            .background(colorResource(com.example.features.R.color.orange))
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "-",
                            fontSize = 22.sp,
                            color = colorResource(com.example.features.R.color.purple_700),
                            fontWeight = FontWeight.W500,
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(5.dp))
                            .clickable {
                                vm.updateHolesCount(holesCount + 1)
                            }
                            .background(colorResource(com.example.features.R.color.orange))
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+",
                            fontSize = 22.sp,
                            color = colorResource(com.example.features.R.color.purple_700),
                            fontWeight = FontWeight.W500,
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }
            }
            Spacer(Modifier.weight(1f))
        }
        MainPathsNavigationRow(
            isDarkMode = isDarkMode,
            selectedPath = MainNavigationPath.Play,
            onClick = { it ->
                when (it) {
                    MainNavigationPath.Profile -> {
                        onNavigateToProfile()
                    }

                    MainNavigationPath.Settings -> {
                        onNavigateToSettings()
                    }

                    else -> {}
                }
            }
        )
    }
}