package com.example.game_vs_bot.ui.theme

import androidx.compose.ui.graphics.Color

enum class ColorScheme(
    val textColor: Int,
    val backgroundColor: Int,
    val holeBackgroundColor: Int,
    val deckBackgroundColor: Int,
) {
    DarkScheme(
        textColor = com.example.features.R.color.white,
        backgroundColor = com.example.features.R.color.black1,
        holeBackgroundColor = com.example.features.R.color.gray1,
        deckBackgroundColor = com.example.features.R.color.black1
    ),
    LightScheme(
        textColor = com.example.features.R.color.black,
        backgroundColor = com.example.features.R.color.white1,
        holeBackgroundColor = com.example.features.R.color.gray2,
        deckBackgroundColor = com.example.features.R.color.white_transparent
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
        rateUsIcon = com.example.features.R.drawable.rate_us_icon_white,
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














