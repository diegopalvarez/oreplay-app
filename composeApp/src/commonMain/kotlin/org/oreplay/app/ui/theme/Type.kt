package org.oreplay.app

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import oreplay_app.composeapp.generated.resources.Inter_18pt_Black
import oreplay_app.composeapp.generated.resources.Inter_18pt_BlackItalic
import oreplay_app.composeapp.generated.resources.Inter_18pt_Bold
import oreplay_app.composeapp.generated.resources.Inter_18pt_BoldItalic
import oreplay_app.composeapp.generated.resources.Inter_18pt_ExtraBold
import oreplay_app.composeapp.generated.resources.Inter_18pt_ExtraBoldItalic
import oreplay_app.composeapp.generated.resources.Inter_18pt_ExtraLight
import oreplay_app.composeapp.generated.resources.Inter_18pt_ExtraLightItalic
import oreplay_app.composeapp.generated.resources.Inter_18pt_Italic
import oreplay_app.composeapp.generated.resources.Inter_18pt_Light
import oreplay_app.composeapp.generated.resources.Inter_18pt_LightItalic
import oreplay_app.composeapp.generated.resources.Inter_18pt_Medium
import oreplay_app.composeapp.generated.resources.Inter_18pt_MediumItalic
import oreplay_app.composeapp.generated.resources.Inter_18pt_Regular
import oreplay_app.composeapp.generated.resources.Inter_18pt_SemiBold
import oreplay_app.composeapp.generated.resources.Inter_18pt_SemiBoldItalic
import oreplay_app.composeapp.generated.resources.Inter_18pt_Thin
import oreplay_app.composeapp.generated.resources.Inter_18pt_ThinItalic
import oreplay_app.composeapp.generated.resources.Res

import org.jetbrains.compose.resources.Font

val Inter @Composable get() = FontFamily(
    Font(Res.font.Inter_18pt_Thin, FontWeight.Thin, FontStyle.Normal),
    Font(Res.font.Inter_18pt_ThinItalic, FontWeight.Thin, FontStyle.Italic),

    Font(Res.font.Inter_18pt_ExtraLight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(Res.font.Inter_18pt_ExtraLightItalic, FontWeight.ExtraLight, FontStyle.Italic),

    Font(Res.font.Inter_18pt_Light, FontWeight.Light, FontStyle.Normal),
    Font(Res.font.Inter_18pt_LightItalic, FontWeight.Light, FontStyle.Italic),

    Font(Res.font.Inter_18pt_Regular, FontWeight.Normal, FontStyle.Normal),
    Font(Res.font.Inter_18pt_Italic, FontWeight.Normal, FontStyle.Italic),

    Font(Res.font.Inter_18pt_Medium, FontWeight.Medium, FontStyle.Normal),
    Font(Res.font.Inter_18pt_MediumItalic, FontWeight.Medium, FontStyle.Italic),

    Font(Res.font.Inter_18pt_SemiBold, FontWeight.SemiBold, FontStyle.Normal),
    Font(Res.font.Inter_18pt_SemiBoldItalic, FontWeight.SemiBold, FontStyle.Italic),

    Font(Res.font.Inter_18pt_Bold, FontWeight.Bold, FontStyle.Normal),
    Font(Res.font.Inter_18pt_BoldItalic, FontWeight.Bold, FontStyle.Italic),

    Font(Res.font.Inter_18pt_ExtraBold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(Res.font.Inter_18pt_ExtraBoldItalic, FontWeight.ExtraBold, FontStyle.Italic),

    Font(Res.font.Inter_18pt_Black, FontWeight.Black, FontStyle.Normal),
    Font(Res.font.Inter_18pt_BlackItalic, FontWeight.Black, FontStyle.Italic),
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography @Composable get() = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = Inter),
    displayMedium = baseline.displayMedium.copy(fontFamily = Inter),
    displaySmall = baseline.displaySmall.copy(fontFamily = Inter),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = Inter),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = Inter),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = Inter),
    titleLarge = baseline.titleLarge.copy(fontFamily = Inter),
    titleMedium = baseline.titleMedium.copy(fontFamily = Inter),
    titleSmall = baseline.titleSmall.copy(fontFamily = Inter),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = Inter),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = Inter),
    bodySmall = baseline.bodySmall.copy(fontFamily = Inter),
    labelLarge = baseline.labelLarge.copy(fontFamily = Inter),
    labelMedium = baseline.labelMedium.copy(fontFamily = Inter),
    labelSmall = baseline.labelSmall.copy(fontFamily = Inter),
)

