package com.example.allticks.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.allticks.R

val provider =
    GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs)
val bodyFontFamily =
    FontFamily(
        Font(
            googleFont = GoogleFont("Inter"),
            fontProvider = provider,
        ))

val displayFontFamily =
    FontFamily(
        Font(
            googleFont = GoogleFont("Inika"),
            fontProvider = provider,
        ))

val Typography =
    Typography(
        titleLarge =
            TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp,
                color = textColor,
                lineHeight = 28.sp,
                letterSpacing = 0.sp),
        titleMedium =
            TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                lineHeight = 28.sp,
                color = textColor,
                letterSpacing = 0.sp),
        bodyMedium =
            TextStyle(
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                color = textColor,
                letterSpacing = 0.5.sp),
        bodyLarge =
            TextStyle(
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                lineHeight = 24.sp,
                color = textColor,
                letterSpacing = 0.5.sp),
        bodySmall =
            TextStyle(
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 18.sp,
                color = textColor,
                letterSpacing = 0.5.sp),
        labelSmall =
            TextStyle(
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                color = textColor,
                letterSpacing = 0.5.sp),
        labelMedium =
            TextStyle(
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                color = textColor,
                letterSpacing = 0.5.sp),
        /* Other default text styles to override
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
        */
        )
