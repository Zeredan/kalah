package com.example.lobby_management.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.features.R
import com.example.lobby_management.domain.LobbyManagementViewModel
import com.example.lobby_management.ui.theme.ColorScheme
import com.example.lobby_management.ui.theme.IconScheme

@Composable
fun LobbyScreen(
    vm: LobbyManagementViewModel,
    onLeave: () -> Unit,
    isDarkMode: Boolean,
    onGameStarted: (gameId: Int) -> Unit
) {
    val colorScheme = if (isDarkMode) ColorScheme.DarkScheme else ColorScheme.LightScheme
    val iconScheme = if (isDarkMode) IconScheme.DarkScheme else IconScheme.LightScheme

    val lobby by vm.selectedLobby.collectAsState()
    val isCreator by vm.isCreatorMode.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(11.dp, 0.dp, 16.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 7.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {
                        onLeave()
                    }
                    .padding(5.dp),
                painter = painterResource(iconScheme.backIcon),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Text(
                text = "${stringResource(R.string.lobby)}: ${lobby?.name ?: "Loading..."}",
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W600
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        lobby?.let { l ->
            Text(
                text = "ID: ${l.id}",
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W500
            )
            Text(
                text = "${stringResource(R.string.players)}:",
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W600
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = l.creator.nickname,
                    fontSize = 22.sp,
                    color = colorResource(colorScheme.textColor),
                    fontWeight = FontWeight.W500
                )
                l.guest?.let {
                    Text(
                        text = it.nickname,
                        fontSize = 22.sp,
                        color = colorResource(colorScheme.textColor),
                        fontWeight = FontWeight.W500
                    )
                }
            }
            Spacer(Modifier.height(56.dp))
            if (isCreator == true) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(colorResource(if (l.guest != null) R.color.purple_500 else colorScheme.cardBackgroundColor))
                        .clickable {
                            vm.startGame()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(com.example.features.R.string.start_game),
                        fontSize = 18.sp,
                        color = colorResource(colorScheme.textColor),
                        fontWeight = FontWeight.W600,
                    )
                }
            }
            else if (isCreator == false) {
                Text(
                    text = stringResource(com.example.features.R.string.waiting),
                    fontSize = 18.sp,
                    color = colorResource(colorScheme.textColor),
                    fontWeight = FontWeight.W600,
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(colorResource(R.color.purple_500))
                        .clickable {
                            onLeave()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(com.example.features.R.string.go_out),
                        fontSize = 18.sp,
                        color = colorResource(colorScheme.textColor),
                        fontWeight = FontWeight.W600,
                    )
                }
            }

        } ?: 0.let{
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
            )
        }
    }

}