package com.example.kalah.ui.theme

import com.example.kalah.R

enum class AppColorScheme(
    val backgroundColor: Int,
    val cardBGColor: Int,
    val textColor: Int,
    val dividerColor: Int,
    val navigationRowColor: Int,
) {
    LightScheme(
        backgroundColor = R.color.white1,
        dividerColor = R.color.black,
        cardBGColor = R.color.teal_200,
        navigationRowColor = R.color.white2,
        textColor = R.color.black
    ),
    DarkScheme(
        backgroundColor = R.color.black1,
        dividerColor = R.color.white,
        cardBGColor = R.color.teal_900,
        navigationRowColor = R.color.black,
        textColor = R.color.white
    )
}