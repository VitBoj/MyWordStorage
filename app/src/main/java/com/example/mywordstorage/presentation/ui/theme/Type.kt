package com.example.mywordstorage.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.mywordstorage.R

val AppTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.ibm_plex_serif_medium)),
        fontSize = 30.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.ibm_plex_serif_medium)),
        fontSize = 25.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.ibm_plex_serif_regular)),
        fontSize = 22.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.2.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.ibm_plex_serif_light)),
        fontSize = 20.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.1.sp
    )
)