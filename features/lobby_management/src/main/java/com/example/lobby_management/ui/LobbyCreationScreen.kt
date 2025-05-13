package com.example.lobby_management.ui

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lobby.model.Lobby
import com.example.lobby_management.domain.LobbyManagementViewModel
import com.example.lobby_management.ui.theme.ColorScheme
import com.example.lobby_management.ui.theme.IconScheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyCreationScreen(
    vm: LobbyManagementViewModel,
    onBack: () -> Unit,
    isDarkMode: Boolean,
    onLobbyCreated: (Lobby) -> Unit
) {
    val colorScheme = if (isDarkMode) ColorScheme.DarkScheme else ColorScheme.LightScheme
    val iconScheme = if (isDarkMode) IconScheme.DarkScheme else IconScheme.LightScheme

    val lobbyName by vm.inputLobbyName.collectAsState()
    val stonesCount by vm.inputStonesCountStr.collectAsState()
    val holesCount by vm.inputHolesCountStr.collectAsState()
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
                text = stringResource(com.example.features.R.string.creation_lobby),
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W600
            )
            Spacer(Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = lobbyName,
            onValueChange = {
                vm.updateLobbyName(it)
            },
            label = {
                Text(
                    text = stringResource(com.example.features.R.string.name),
                    fontSize = 18.sp,
                    color = colorResource(colorScheme.textColor),
                    fontWeight = FontWeight.W600,
                )
            },
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(colorScheme.textColor),
                unfocusedTextColor = colorResource(colorScheme.textColor),
                focusedContainerColor = colorResource(colorScheme.backgroundColor),
                unfocusedContainerColor = colorResource(colorScheme.backgroundColor),
                focusedLabelColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent
            )
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = holesCount,
            onValueChange = {
                vm.updateHolesCountStr(it)
            },
            label = {
                Text(
                    text = stringResource(com.example.features.R.string.holes),
                    fontSize = 18.sp,
                    color = colorResource(colorScheme.textColor),
                    fontWeight = FontWeight.W600,
                )
            },
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(colorScheme.textColor),
                unfocusedTextColor = colorResource(colorScheme.textColor),
                focusedContainerColor = colorResource(colorScheme.backgroundColor),
                unfocusedContainerColor = colorResource(colorScheme.backgroundColor),
                focusedLabelColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent
            )
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = stonesCount,
            onValueChange = {
                vm.updateStonesCountStr(it)
            },
            label = {
                Text(
                    text = stringResource(com.example.features.R.string.stones),
                    fontSize = 18.sp,
                    color = colorResource(colorScheme.textColor),
                    fontWeight = FontWeight.W600,
                )
            },
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(colorScheme.textColor),
                unfocusedTextColor = colorResource(colorScheme.textColor),
                focusedContainerColor = colorResource(colorScheme.backgroundColor),
                unfocusedContainerColor = colorResource(colorScheme.backgroundColor),
                focusedLabelColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent
            )
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .height(56.dp)
                .background(colorResource(com.example.features.R.color.purple_500))
                .clickable {
                    coroutineScope.launch {
                        val result = vm.createLobby().await()
                        if (result != null) {
                            onLobbyCreated(result)
                        } else {
                            Toast.makeText(
                                context,
                                "Can\'t create lobby",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(com.example.features.R.string.create),
                fontSize = 18.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W600,
            )
        }
    }
}