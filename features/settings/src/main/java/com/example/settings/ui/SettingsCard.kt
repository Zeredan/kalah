package com.example.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.settings.ui.theme.ColorScheme
import com.example.settings.ui.theme.IconScheme

@Composable
fun SettingCard(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    size: Dp = 56.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkMode) ColorScheme.DarkScheme else ColorScheme.LightScheme
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(size)
            .clip(RoundedCornerShape(16.dp))
            .run{
                if (onClick != null) clickable(onClick = onClick) else this
            }
            .background(colorResource(colorScheme.cardBackgroundColor))
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}