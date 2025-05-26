package com.example.profile.ui

import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.features.model.MainNavigationPath
import com.example.features.ui.MainPathsNavigationRow
import com.example.profile.R
import com.example.profile.domain.ProfileViewModel
import com.example.profile.ui.theme.ColorScheme
import com.example.profile.ui.theme.IconScheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileFeatureRoot(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    vm: ProfileViewModel,
    onNavigateToPlay: () -> Unit,
    onNavigateToSettings: () -> Unit,
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
                    text = stringResource(com.example.features.R.string.profile),
                    fontSize = 22.sp,
                    color = colorResource(colorScheme.textColor),
                    fontWeight = FontWeight.W600,
                )
                Spacer(Modifier.weight(1f))
            }
            val myUser by vm.myProfileSavedUser.collectAsState()
            if (vm.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(50.dp)
                )
            } else {
                val login by vm.registerLogin.collectAsState()
                val password by vm.registerPassword.collectAsState()
                val isLoginValid = remember(login) {
                    login.isNotEmpty() && login.length in 5..20
                }
                val isPasswordValid = remember(password) {
                    password.isNotEmpty() && password.length in 5..20
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp, 20.dp, 16.dp, 0.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    myUser?.let { user ->
                        stickyHeader {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = user.nickname,
                                    fontSize = 20.sp,
                                    color = colorResource(colorScheme.textColor),
                                    fontWeight = FontWeight.W600,
                                )
                            }
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = stringResource(com.example.features.R.string.statistics),
                                    fontSize = 18.sp,
                                    color = colorResource(colorScheme.textColor),
                                    fontWeight = FontWeight.W600,
                                )
                            }
                        }
                        items(user.wins.toList()) { (diff, wins) ->
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(com.example.features.R.string.wins_vs_bot) + when (diff) {
                                        "easy" -> stringResource(com.example.features.R.string.easy)
                                        "medium" -> stringResource(com.example.features.R.string.medium)
                                        "hard" -> stringResource(com.example.features.R.string.hard)
                                        else -> "???"
                                    },
                                    fontSize = 16.sp,
                                    color = colorResource(colorScheme.textColor),
                                    fontWeight = FontWeight.W500,
                                )
                                Text(
                                    text = wins.toString(),
                                    fontSize = 16.sp,
                                    color = colorResource(colorScheme.textColor),
                                    fontWeight = FontWeight.W500,
                                )
                            }
                        }
                        item {
                            Spacer(Modifier.weight(1f))
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .background(colorResource(colorScheme.activatedBackgroundColor))
                                    .clickable {
                                        vm.logOut()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(com.example.features.R.string.log_out),
                                    fontSize = 18.sp,
                                    color = colorResource(colorScheme.textColor),
                                    fontWeight = FontWeight.W600,
                                )
                            }
                        }
                    } ?: let {
                        item {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(com.example.features.R.string.not_registrated),
                                    fontSize = 20.sp,
                                    color = colorResource(colorScheme.textColor),
                                    fontWeight = FontWeight.W600,
                                )
                            }
                        }
                        item {
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                value = login,
                                isError = !isLoginValid,
                                supportingText = {
                                    if (!isLoginValid) {
                                        Text(
                                            text = stringResource(com.example.features.R.string.login_error),
                                            fontSize = 18.sp,
                                            color = colorResource(com.example.features.R.color.red),
                                            fontWeight = FontWeight.W600,
                                        )
                                    }
                                },
                                onValueChange = {
                                    if (!it.contains('\n')) vm.updateRegisterLogin(it)
                                },
                                label = {
                                    Text(
                                        text = stringResource(com.example.features.R.string.login),
                                        fontSize = 18.sp,
                                        color = colorResource(colorScheme.textColor),
                                        fontWeight = FontWeight.W600,
                                    )
                                },
                                maxLines = 1,
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = colorResource(colorScheme.textColor),
                                    unfocusedTextColor = colorResource(colorScheme.textColor),
                                    errorTextColor = colorResource(colorScheme.textColor),

                                    focusedContainerColor = colorResource(colorScheme.backgroundColor),
                                    unfocusedContainerColor = colorResource(colorScheme.backgroundColor),
                                    errorContainerColor = colorResource(colorScheme.backgroundColor),

                                    focusedLabelColor = Color.Transparent,
                                    unfocusedLabelColor = Color.Transparent,
                                    errorLabelColor = Color.Transparent,
                                )
                            )
                        }
                        item {
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                value = password,
                                isError = !isPasswordValid,
                                supportingText = {
                                    if (!isPasswordValid) {
                                        Text(
                                            text = stringResource(com.example.features.R.string.password_error),
                                            fontSize = 18.sp,
                                            color = colorResource(com.example.features.R.color.red),
                                            fontWeight = FontWeight.W600,
                                        )
                                    }
                                },
                                visualTransformation = { text ->
                                    TransformedText(
                                        AnnotatedString("*".repeat(text.text.length)),
                                        OffsetMapping.Identity
                                    )
                                },
                                onValueChange = {
                                    if (!it.contains('\n')) vm.updateRegisterPassword(it)
                                },
                                label = {
                                    Text(
                                        text = stringResource(com.example.features.R.string.password),
                                        fontSize = 18.sp,
                                        color = colorResource(colorScheme.textColor),
                                        fontWeight = FontWeight.W600,
                                    )
                                },
                                maxLines = 1,
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = colorResource(colorScheme.textColor),
                                    unfocusedTextColor = colorResource(colorScheme.textColor),
                                    errorTextColor = colorResource(colorScheme.textColor),

                                    focusedContainerColor = colorResource(colorScheme.backgroundColor),
                                    unfocusedContainerColor = colorResource(colorScheme.backgroundColor),
                                    errorContainerColor = colorResource(colorScheme.backgroundColor),

                                    focusedLabelColor = Color.Transparent,
                                    unfocusedLabelColor = Color.Transparent,
                                    errorLabelColor = Color.Transparent,
                                )
                            )
                        }
                        item {
                            val isActive = login.isNotBlank() && password.isNotBlank() && isLoginValid && isPasswordValid
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .background(colorResource(
                                        if (!isActive) colorScheme.cardBackgroundColor
                                        else colorScheme.activatedBackgroundColor)
                                    )
                                    .run {
                                        if (isActive) this.clickable {
                                            vm.register()
                                        } else this
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(com.example.features.R.string.log_in),
                                    fontSize = 18.sp,
                                    color = colorResource(colorScheme.textColor),
                                    fontWeight = FontWeight.W600,
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(74.dp))
        }
        MainPathsNavigationRow(
            isDarkMode = isDarkMode,
            selectedPath = MainNavigationPath.Profile,
            onClick = { it ->
                when (it) {
                    MainNavigationPath.Play -> {
                        onNavigateToPlay()
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