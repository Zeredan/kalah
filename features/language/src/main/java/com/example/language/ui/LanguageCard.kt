package com.example.language.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.features.ui.MarkSelector
import com.example.language.ui.theme.ColorScheme

@Composable
fun LanguageCard(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    selected: Boolean,
    isDarkMode: Boolean,
    onClick: () -> Unit
) {
    val colorScheme = if (isDarkMode) ColorScheme.DarkScheme else ColorScheme.LightScheme

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .background(colorResource(colorScheme.languageCardBackgroundColor))
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(3.dp))
                .size(28.dp, 20.dp),
            painter = painterResource(icon),
            contentDescription = null
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text,
            color = colorResource(colorScheme.textColor),
            fontSize = 14.sp,
            fontWeight = FontWeight.W500
        )
        Spacer(Modifier.weight(1f))
        MarkSelector(
            selected = selected,
            isDarkMode = isDarkMode
        )
    }
}