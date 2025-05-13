package com.example.features.ui

import androidx.activity.ComponentActivity
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.EaseInOutQuint
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.features.R
import com.example.features.model.MainNavigationPath
import com.example.features.ui.theme.MainNavigationColorScheme

@Composable
fun MainPathsNavigationRow(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    selectedPath: MainNavigationPath,
    onClick: (MainNavigationPath) -> Unit
) {
    val colorScheme = if (isDarkMode) MainNavigationColorScheme.DarkScheme else MainNavigationColorScheme.LightScheme
    val context = LocalContext.current
    HorizontalDivider(
        thickness = 2.dp,
        color = colorResource(colorScheme.dividerColor)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .background(colorResource(colorScheme.navigationRowColor))
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val animatedColor1 by rememberInfiniteTransition("navBorderColor1")
            .animateColor(
                label = "navBorderColor1",
                initialValue = Color(60, 70, 170),
                targetValue = Color(30, 180, 150),
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = EaseInOutQuint),
                    repeatMode = RepeatMode.Reverse
                )
            )
        val animatedColor2 by rememberInfiniteTransition("navBorderColor2")
            .animateColor(
                label = "navBorderColor2",
                initialValue = Color(30, 180, 150),
                targetValue = Color(60, 70, 170),
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = EaseInOutQuint),
                    repeatMode = RepeatMode.Reverse
                )
            )
        animatedColor1
        animatedColor2
        MainNavigationPath.entries.forEach { navElement ->
            val isSelected = navElement == selectedPath
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .run{
                        if (!isSelected) {
                            this.clickable {
                                onClick(navElement)
                            }
                        } else this
                    },
                painter = painterResource(
                    when(navElement) {
                        MainNavigationPath.Play -> R.drawable.play
                        MainNavigationPath.Profile -> R.drawable.profile
                        MainNavigationPath.Settings -> R.drawable.settings
                    }
                ),
                contentDescription = null
            )
        }
    }
}