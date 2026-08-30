package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val HackDarkColorScheme = darkColorScheme(
    primary = HackAccentGreen,
    onPrimary = HackBackground,
    primaryContainer = HackSurfaceVariant,
    onPrimaryContainer = HackAccentGreen,

    secondary = HackAccentViolet,
    onSecondary = HackTextPrimary,
    secondaryContainer = HackSurfaceVariant,
    onSecondaryContainer = HackAccentViolet,

    tertiary = HackWarningOrange,
    onTertiary = HackBackground,
    tertiaryContainer = HackSurfaceVariant,
    onTertiaryContainer = HackWarningOrange,

    background = HackBackground,
    onBackground = HackTextPrimary,

    surface = HackSurface,
    onSurface = HackTextPrimary,
    surfaceVariant = HackSurfaceVariant,
    onSurfaceVariant = HackTextMuted,

    outline = HackBorder,
    outlineVariant = HackBorderBright,
    error = HackErrorRed,
    onError = HackTextPrimary
)

@Composable
fun HackPathTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = HackDarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = HackBackground.toArgb()
            window.navigationBarColor = HackBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = HackTypography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    HackPathTheme(content = content)
}
