package com.example.terms_of_use.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.terms_of_use.ui.theme.ColorScheme
import com.example.terms_of_use.ui.theme.IconScheme


@Composable
fun AboutDevelopersFeatureRoot(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    onBack: () -> Unit
) {
    val colorScheme = if (isDarkMode) ColorScheme.DarkScheme else ColorScheme.LightScheme
    val iconScheme = if (isDarkMode) IconScheme.DarkScheme else IconScheme.LightScheme
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(colorScheme.backgroundColor))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(11.dp, 0.dp, 0.dp, 16.dp),
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
                text = stringResource(com.example.features.R.string.about_developers),
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W600,
            )
            Spacer(Modifier.weight(1f))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(com.example.features.R.string.vladimir_title),
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W600,
            )
        }
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(com.example.features.R.string.vladimir_description),
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W500,
            )
        }
        Image(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            painter = painterResource(com.example.features.R.drawable.vladimir),
            contentDescription = null
        )
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(com.example.features.R.string.vladimir_impact),
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W500,
            )
        }
        Spacer(Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(com.example.features.R.string.alexandr_title),
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W600,
            )
        }
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(com.example.features.R.string.alexandr_description),
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W500,
            )
        }
        Image(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            painter = painterResource(com.example.features.R.drawable.alexandr),
            contentDescription = null
        )
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(com.example.features.R.string.alexandr_impact),
                fontSize = 22.sp,
                color = colorResource(colorScheme.textColor),
                fontWeight = FontWeight.W500,
            )
        }
    }
}