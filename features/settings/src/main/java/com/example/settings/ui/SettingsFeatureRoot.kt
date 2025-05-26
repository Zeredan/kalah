package com.example.settings.ui

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.features.model.MainNavigationPath
import com.example.features.ui.MainPathsNavigationRow
import com.example.settings.R
import com.example.settings.domain.SettingsViewModel
import com.example.settings.model.GameTheme
import com.example.settings.ui.theme.ColorScheme
import com.example.settings.ui.theme.IconScheme

@Composable
fun SettingsFeatureRoot(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    vm: SettingsViewModel = hiltViewModel(),
    onLangClicked: () -> Unit,
    onNavigateToPlay: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onAboutDevsClicked: () -> Unit,
    onAboutSystemClicked: () -> Unit
) {
    val colorScheme = if (isDarkMode) ColorScheme.DarkScheme else ColorScheme.LightScheme
    val iconScheme = if (isDarkMode) IconScheme.DarkScheme else IconScheme.LightScheme
    val languageMapping = mapOf(
        "en" to com.example.features.R.string.english,
        "ru" to com.example.features.R.string.russian,
        "es" to com.example.features.R.string.spanish,
        "pt" to com.example.features.R.string.portuguese
    )

    val selectedLanguage by vm.selectedLanguageStateFlow.collectAsState()
    val selectedGameTheme by vm.selectedGameTheme.collectAsState()
    val savedBaseURI by vm.savedBaseUriStateFlow.collectAsState()

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
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(com.example.features.R.string.settings),
                    fontSize = 22.sp,
                    color = colorResource(colorScheme.textColor),
                    fontWeight = FontWeight.W600,
                )
                Spacer(Modifier.weight(1f))
            }
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp, 20.dp, 16.dp, 0.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                var savedBaseUri by remember(savedBaseURI) { mutableStateOf(savedBaseURI) }
                SettingCard(
                    isDarkMode = isDarkMode,
                    size = 120.dp
                ) {
                    Image(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(20.dp),
                        painter = painterResource(iconScheme.langIcon),
                        contentDescription = null
                    )
                    Text(
                        text = "BASE URI: ",
                        fontSize = 14.sp,
                        color = colorResource(colorScheme.textColor),
                        fontWeight = FontWeight.W600
                    )
                    Spacer(Modifier.weight(1f))
                    TextField(
                        modifier = Modifier
                            .width(200.dp)
                            .weight(2f),
                        value = savedBaseUri,
                        onValueChange = {
                            savedBaseUri = it
                            vm.updateSavedBaseUri(it)
                        },
                        label = {
                            Text(
                                text = "URI",
                                fontSize = 18.sp,
                                color = colorResource(colorScheme.textColor),
                                fontWeight = FontWeight.W600,
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = colorResource(colorScheme.textColor),
                            unfocusedTextColor = colorResource(colorScheme.textColor),
                            focusedContainerColor = colorResource(colorScheme.backgroundColor),
                            unfocusedContainerColor = colorResource(colorScheme.backgroundColor),
                            focusedLabelColor = Color.Transparent,
                            unfocusedLabelColor = Color.Transparent
                        )
                    )
                }
                SettingCard(
                    isDarkMode = isDarkMode,
                    onClick = {
                        onLangClicked()
                    }
                ) {
                    Image(
                        modifier = Modifier
                            .padding(end = 12.dp),
                        painter = painterResource(iconScheme.langIcon),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(com.example.features.R.string.language),
                        fontSize = 14.sp,
                        color = colorResource(colorScheme.textColor),
                        fontWeight = FontWeight.W600
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = if (selectedLanguage != null) {
                            stringResource(languageMapping[selectedLanguage!!] ?: com.example.features.R.string.english)
                        } else {
                            stringResource(com.example.features.R.string.english)
                        },
                        fontSize = 14.sp,
                        color = colorResource(com.example.features.R.color.blue),
                        fontWeight = FontWeight.W500
                    )
                }
                SettingCard(
                    isDarkMode = isDarkMode
                ) {
                    Image(
                        modifier = Modifier
                            .padding(end = 12.dp),
                        painter = painterResource(iconScheme.darkModeIcon),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(com.example.features.R.string.dark_mode),
                        fontSize = 14.sp,
                        color = colorResource(colorScheme.textColor),
                        fontWeight = FontWeight.W600
                    )
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = vm::updateDarkMode,
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = colorResource(com.example.features.R.color.blue),
                            uncheckedTrackColor = colorResource(com.example.features.R.color.white1),
                            checkedThumbColor = colorResource(com.example.features.R.color.white),
                            uncheckedThumbColor = colorResource(com.example.features.R.color.white),
                            checkedBorderColor = colorResource(com.example.features.R.color.blue),
                            uncheckedBorderColor = colorResource(com.example.features.R.color.white1),
                        ),
                        thumbContent = {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(28.dp)
                                    .background(colorResource(com.example.features.R.color.white))
                            )
                        }
                    )
                }
                SettingCard(
                    isDarkMode = isDarkMode,
                    onClick = {
                        onAboutDevsClicked()
                    }
                ) {
                    Image(
                        modifier = Modifier
                            .padding(end = 12.dp),
                        painter = painterResource(iconScheme.privacyPolicyIcon),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(com.example.features.R.string.about_developers),
                        fontSize = 14.sp,
                        color = colorResource(colorScheme.textColor),
                        fontWeight = FontWeight.W600
                    )
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(iconScheme.forwardIcon),
                        contentDescription = null
                    )
                }
                SettingCard(
                    isDarkMode = isDarkMode,
                    onClick = {
                        onAboutSystemClicked()
                    }
                ) {
                    Image(
                        modifier = Modifier
                            .padding(end = 12.dp),
                        painter = painterResource(iconScheme.rateUsIcon),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(com.example.features.R.string.about_system),
                        fontSize = 14.sp,
                        color = colorResource(colorScheme.textColor),
                        fontWeight = FontWeight.W600
                    )
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(iconScheme.forwardIcon),
                        contentDescription = null
                    )
                }
                var isGameThemeExpanded by remember { mutableStateOf(false) }
                Column {
                    SettingCard(
                        isDarkMode = isDarkMode,
                        onClick = {
                            isGameThemeExpanded = true
                        }
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(end = 12.dp),
                            painter = painterResource(iconScheme.themeIcon),
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(com.example.features.R.string.game_theme),
                            fontSize = 14.sp,
                            color = colorResource(colorScheme.textColor),
                            fontWeight = FontWeight.W600
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = when(selectedGameTheme) {
                                GameTheme.WHITE_TREE -> stringResource(com.example.features.R.string.white_tree_theme)
                                GameTheme.CREAM_TREE -> stringResource(com.example.features.R.string.cream_tree_theme)
                                GameTheme.BLACK_TREE -> stringResource(com.example.features.R.string.black_tree_theme)
                                GameTheme.BROWN_TREE -> stringResource(com.example.features.R.string.brown_tree_theme)
                                GameTheme.GINGER_TREE -> stringResource(com.example.features.R.string.ginger_tree_theme)
                                else -> ""
                            },
                            fontSize = 14.sp,
                            color = colorResource(com.example.features.R.color.orange),
                            fontWeight = FontWeight.W500
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(colorScheme.backgroundColor)),
                        expanded = isGameThemeExpanded,
                        onDismissRequest = {
                            isGameThemeExpanded = false
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .height(300.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            listOf(
                                GameTheme.WHITE_TREE,
                                GameTheme.CREAM_TREE,
                                GameTheme.BLACK_TREE,
                                GameTheme.BROWN_TREE,
                                GameTheme.GINGER_TREE
                            ).forEach { theme ->
                                Row(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .clickable {
                                            vm.updateGameTheme(theme)
                                            isGameThemeExpanded = false
                                        }
                                        .background(colorResource(colorScheme.cardBackgroundColor)),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = when(theme) {
                                            GameTheme.WHITE_TREE -> stringResource(com.example.features.R.string.white_tree_theme)
                                            GameTheme.CREAM_TREE -> stringResource(com.example.features.R.string.cream_tree_theme)
                                            GameTheme.BLACK_TREE -> stringResource(com.example.features.R.string.black_tree_theme)
                                            GameTheme.BROWN_TREE -> stringResource(com.example.features.R.string.brown_tree_theme)
                                            GameTheme.GINGER_TREE -> stringResource(com.example.features.R.string.ginger_tree_theme)
                                            else -> ""
                                        },
                                        fontSize = 16.sp,
                                        color = colorResource(com.example.features.R.color.orange),
                                        fontWeight = FontWeight.W600
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(74.dp))
        }
        MainPathsNavigationRow(
            isDarkMode = isDarkMode,
            selectedPath = MainNavigationPath.Settings,
            onClick = {
                when (it) {
                    MainNavigationPath.Play -> {
                        onNavigateToPlay()
                    }

                    MainNavigationPath.Profile -> {
                        onNavigateToProfile()
                    }

                    else -> {}
                }
            }
        )
    }
}
