package com.example.settings.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.settings.R

enum class ColorScheme(
    val textColor: Int,
    val backgroundColor: Int,
    val cardBackgroundColor: Int,
    val checkBorderColor: Int
) {
    DarkScheme(
        textColor = com.example.features.R.color.white,
        backgroundColor = com.example.features.R.color.black1,
        cardBackgroundColor = com.example.features.R.color.black2,
        checkBorderColor = com.example.features.R.color.white
    ),
    LightScheme(
        textColor = com.example.features.R.color.black,
        backgroundColor = com.example.features.R.color.white1,
        cardBackgroundColor = com.example.features.R.color.white,
        checkBorderColor = com.example.features.R.color.black
    )
}

enum class IconScheme(
    val backIcon: Int,
    val langIcon: Int,
    val darkModeIcon: Int,
    val privacyPolicyIcon: Int,
    val rateUsIcon: Int,
    val forwardIcon: Int,
    val themeIcon: Int,
) {
    DarkScheme(
        backIcon = com.example.features.R.drawable.back_icon_white,
        langIcon = com.example.features.R.drawable.lang_icon_white,
        darkModeIcon = com.example.features.R.drawable.dark_mode_icon_white,
        privacyPolicyIcon = com.example.features.R.drawable.privacy_policy_icon_white,
        rateUsIcon =com.example.features. R.drawable.rate_us_icon_white,
        forwardIcon = com.example.features.R.drawable.forward_icon_white,
        themeIcon = com.example.features.R.drawable.beach_white
    ),
    LightScheme(
        backIcon = com.example.features.R.drawable.back_icon_black,
        langIcon = com.example.features.R.drawable.lang_icon_black,
        darkModeIcon = com.example.features.R.drawable.dark_mode_icon_black,
        privacyPolicyIcon = com.example.features.R.drawable.privacy_policy_icon_black,
        rateUsIcon = com.example.features.R.drawable.rate_us_icon_black,
        forwardIcon = com.example.features.R.drawable.forward_icon_black,
        themeIcon = com.example.features.R.drawable.beach_black
    )
}














