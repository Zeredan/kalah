package com.example.language.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.language.R
import com.example.language.domain.LanguageModel
import com.example.language.ui.theme.ColorScheme
import com.example.language.ui.theme.IconScheme
import com.example.language.domain.LanguageViewModel

@Composable
fun LanguageFeatureRoot(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    vm: LanguageViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onOkay: () -> Unit,
) {
    val colorScheme = if (isDarkMode) ColorScheme.DarkScheme else ColorScheme.LightScheme
    val iconScheme = if (isDarkMode) IconScheme.DarkScheme else IconScheme.LightScheme
    val showOkMark by vm.showOkayMark.collectAsState()
    val langData = listOf(
        LanguageModel(
            "ru",
            stringResource(com.example.features.R.string.russian),
            com.example.features.R.drawable.rus_flag
        ),
        LanguageModel(
            "en",
            stringResource(com.example.features.R.string.english),
            com.example.features.R.drawable.english_flag
        ),
        LanguageModel(
            "pt",
            stringResource(com.example.features.R.string.portuguese),
            com.example.features.R.drawable.portuguese_flag
        ),
        LanguageModel(
            "es",
            stringResource(com.example.features.R.string.spanish),
            com.example.features.R.drawable.spanish_flag
        ),
    )
    val selectedLanguage by vm.selectedLanguageStateFlow.collectAsState()
    val preSelectedLanguage by vm.preSelectedLanguageStateFlow.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        onBack()
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(colorScheme.backgroundColor)),
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
                text = stringResource(com.example.features.R.string.language),
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W600
            )
            Spacer(Modifier.weight(1f))
            if (showOkMark) {
                Image(
                    modifier = modifier
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            vm.selectLanguage()
                            onOkay()
                        }
                        .padding(5.dp),
                    painter = painterResource(iconScheme.checkIcon),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp, 20.dp, 16.dp, 0.dp),
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(1) {
                    langData.forEach { (lang, text, img) ->
                        LanguageCard(
                            text = text,
                            icon = img,
                            selected = (preSelectedLanguage == lang),
                            isDarkMode = isDarkMode
                        ) {
                            vm.preSelectLanguage(lang)
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(74.dp))
    }

}