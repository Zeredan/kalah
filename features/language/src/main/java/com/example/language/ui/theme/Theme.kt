package com.example.language.ui.theme

import com.example.language.R


enum class ColorScheme(
    val textColor: Int,
    val backgroundColor: Int,
    val languageCardBackgroundColor: Int,
) {
    DarkScheme(
        textColor = com.example.features.R.color.white,
        backgroundColor = com.example.features.R.color.black1,
        languageCardBackgroundColor = com.example.features.R.color.black2,
    ),
    LightScheme(
        textColor = com.example.features.R.color.black,
        backgroundColor = com.example.features.R.color.white1,
        languageCardBackgroundColor = com.example.features.R.color.white,
    )
}

enum class IconScheme(
    val backIcon: Int,
    val checkIcon: Int,
) {
    DarkScheme(
        backIcon = com.example.features.R.drawable.back_icon_white,
        checkIcon = com.example.features.R.drawable.check_icon_white,
    ),
    LightScheme(
        backIcon = com.example.features.R.drawable.back_icon_black,
        checkIcon = com.example.features.R.drawable.check_icon_black,
    )
}














