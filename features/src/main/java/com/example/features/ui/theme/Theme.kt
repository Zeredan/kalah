package com.example.features.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.features.R

enum class NoInternetScheme(
    val textColor: Int,
    val backgroundColor: Int,
) {
    DarkScheme(
        textColor = R.color.white,
        backgroundColor = R.color.black1
    ),
    LightScheme(
        textColor = R.color.black,
        backgroundColor = R.color.white1,
    )
}

enum class DialogColorScheme(
    val textColor: Int,
    val backgroundColor: Int,
    val trackColor: Int
) {
    DarkScheme(
        textColor = R.color.white,
        backgroundColor = R.color.gray1,
        trackColor = R.color.gray2
    ),
    LightScheme(
        textColor = R.color.black,
        backgroundColor = R.color.white1,
        trackColor = R.color.white2
    )
}

enum class RecoveredDialogColorScheme(
    val textColor: Int,
    val backgroundColor: Int,
    val imageBackgroundColor: Int
) {
    DarkScheme(
        textColor = R.color.white,
        backgroundColor = R.color.black1,
        imageBackgroundColor = R.color.black2,
    ),
    LightScheme(
        textColor = R.color.black,
        backgroundColor = R.color.white1,
        imageBackgroundColor = R.color.gray,
    )
}



enum class MarkSelectorColorScheme(
    val checkBorderColor: Int,
) {
    DarkScheme(
        checkBorderColor = R.color.white
    ),
    LightScheme(
        checkBorderColor = R.color.black
    )
}

enum class MarkSelectorIconScheme(
    val checkIcon: Int
) {
    DarkScheme(
        checkIcon = R.drawable.selected_mark_black
    ),
    LightScheme(
        checkIcon = R.drawable.selected_mark_white
    )
}

enum class DocumentColorScheme(
    val textColor: Int,
    val secondaryTextColor: Int,
    val backgroundColor: Int,
    val cardBackgroundColor: Int,
    val rowCardBackgroundColor: Int
) {
    DarkScheme(
        textColor = R.color.white,
        secondaryTextColor = R.color.white_transparent,
        backgroundColor = R.color.black1,
        cardBackgroundColor = R.color.gray1,
        rowCardBackgroundColor = R.color.black2
    ),
    LightScheme(
        textColor = R.color.black,
        secondaryTextColor = R.color.black_transparent,
        backgroundColor = R.color.white1,
        cardBackgroundColor = R.color.gray,
        rowCardBackgroundColor = R.color.white
    )
}

enum class MainNavigationColorScheme(
    val cardBGColor: Int,
    val dividerColor: Int,
    val navigationRowColor: Int,
) {
    LightScheme(
        dividerColor = R.color.black,
        cardBGColor = R.color.teal_200,
        navigationRowColor = R.color.white2
    ),
    DarkScheme(
        dividerColor = R.color.white,
        cardBGColor = R.color.teal_900,
        navigationRowColor = R.color.black
    )
}








