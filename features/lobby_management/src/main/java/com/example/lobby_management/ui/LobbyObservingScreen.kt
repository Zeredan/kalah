package com.example.lobby_management.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.lobby.model.Lobby
import com.example.lobby_management.R
import com.example.lobby_management.domain.LobbyManagementViewModel
import com.example.lobby_management.ui.theme.ColorScheme
import com.example.lobby_management.ui.theme.IconScheme
import kotlinx.coroutines.launch

@Composable
fun LobbyObservingScreen(
    vm: LobbyManagementViewModel,
    onCreateLobby: () -> Unit,
    onJoinLobby: (Lobby) -> Unit,
    isDarkMode: Boolean,
    onBack: () -> Unit
) {
    val colorScheme = if (isDarkMode) ColorScheme.DarkScheme else ColorScheme.LightScheme
    val iconScheme = if (isDarkMode) IconScheme.DarkScheme else IconScheme.LightScheme
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                        onBack()
                    }
                    .padding(5.dp),
                painter = painterResource(iconScheme.backIcon),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Text(
                text = stringResource(com.example.features.R.string.lobbies),
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W600
            )
            Spacer(Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))

        val lobbies = vm.availableLobbies
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(
                    width = 3.dp,
                    color = colorResource(com.example.features.R.color.purple_500),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            items(lobbies) { lobby ->
                LobbyItem(
                    lobby = lobby,
                    onClick = {
                        coroutineScope.launch {
                            val result = vm.joinLobby(lobby.id).await()
                            if (result) {
                                onJoinLobby(lobby)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Can\'t join lobby",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    isDarkMode = isDarkMode
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(colorScheme.playBackgroundColor))
                    .clickable { onCreateLobby() }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    color = colorResource(com.example.features.R.color.orange)
                )
            }
            Spacer(Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(colorScheme.playBackgroundColor))
                    .clickable { vm.reloadLobbies() }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Reload",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    color = colorResource(com.example.features.R.color.orange)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

    }
}

@Composable
private fun LobbyItem(
    lobby: Lobby,
    onClick: () -> Unit,
    isDarkMode: Boolean
) {
    val colorScheme = if (isDarkMode) ColorScheme.DarkScheme else ColorScheme.LightScheme
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(colorScheme.cardBackgroundColor))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = lobby.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(colorScheme.textColor)
                )
                Text(
                    text = "ID: ${lobby.id}",
                    fontSize = 12.sp,
                    color = colorResource(colorScheme.textColor)
                )
            }
            Text(
                text = "${stringResource(com.example.features.R.string.players)}: ${lobby.guest?.let{2} ?: 1}",
                fontSize = 14.sp,
                fontWeight = FontWeight.W600,
                color = colorResource(colorScheme.textColor)
            )
        }
    }
}