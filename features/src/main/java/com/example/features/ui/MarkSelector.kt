package com.example.features.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.features.R
import com.example.features.ui.theme.MarkSelectorColorScheme
import com.example.features.ui.theme.MarkSelectorIconScheme

@Composable
fun MarkSelector(
    modifier: Modifier = Modifier,
    afterModifier: Modifier = Modifier,
    selected: Boolean,
    isDarkMode: Boolean
) {
    val colorScheme = if (isDarkMode) MarkSelectorColorScheme.DarkScheme else MarkSelectorColorScheme.LightScheme
    val iconScheme = if (isDarkMode) MarkSelectorIconScheme.DarkScheme else MarkSelectorIconScheme.LightScheme

    if (selected) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .border(1.5.dp, colorResource(R.color.blue1), shape = CircleShape)
                .background(colorResource(R.color.blue1))
                .size(20.dp)
                .then(afterModifier),
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier = Modifier
                    .size(10.dp),
                painter = painterResource(iconScheme.checkIcon),
                contentDescription = null
            )
        }
    }
    else{
        Box(
            modifier = modifier
                .clip(CircleShape)
                .border(1.5.dp, colorResource(colorScheme.checkBorderColor), shape = CircleShape)
                .size(20.dp)
                .then(afterModifier)
        )
    }
}