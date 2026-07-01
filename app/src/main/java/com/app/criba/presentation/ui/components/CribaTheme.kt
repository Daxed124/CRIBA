package com.app.criba.presentation.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ===== CRIBA Brand Colors (from logo) =====
val CribaPrimary = Color(0xFF1B4332)         // verde oscuro — borde círculo logo
val CribaSecondary = Color(0xFF2D6A4F)       // verde medio — planta/hojas
val CribaAccent = Color(0xFFC8A84B)          // dorado — sol/campo
val CribaEarth = Color(0xFF7B4F2E)           // marrón — tierra
val CribaNavyBlue = Color(0xFF1B2B4B)        // azul marino — texto "CRIBA"
val CribaNetBlue = Color(0xFF2B6CB0)         // azul red — nodos conectividad
val CribaBackground = Color(0xFFF8F7F2)      // crema neutro — fondo general
val CribaSurface = Color(0xFFFFFFFF)         // blanco — cards
val CribaOnPrimary = Color.White
val CribaOnBackground = Color(0xFF1C1B1F)
val CribaOnSurface = Color(0xFF1C1B1F)
val CribaError = Color(0xFFD32F2F)

// Dark theme
private val CribaDarkBackground = Color(0xFF121212)
private val CribaDarkSurface = Color(0xFF1E1E1E)
private val CribaDarkOnBackground = Color(0xFFE0E0E0)
private val CribaDarkOnSurface = Color(0xFFE0E0E0)

private val LightColorScheme = lightColorScheme(
    primary = CribaPrimary,
    onPrimary = CribaOnPrimary,
    primaryContainer = CribaSecondary,
    onPrimaryContainer = Color.White,
    secondary = CribaAccent,
    onSecondary = CribaNavyBlue,
    secondaryContainer = Color(0xFFFFF8E7),
    onSecondaryContainer = CribaEarth,
    tertiary = CribaEarth,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFD4EDDA),
    error = CribaError,
    onError = Color.White,
    background = CribaBackground,
    onBackground = CribaOnBackground,
    surface = CribaSurface,
    onSurface = CribaOnSurface,
    surfaceVariant = Color(0xFFE8E8E3),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
)

private val DarkColorScheme = darkColorScheme(
    primary = CribaSecondary,
    onPrimary = Color.White,
    primaryContainer = CribaPrimary,
    onPrimaryContainer = Color(0xFFB8DFC5),
    secondary = CribaAccent,
    onSecondary = CribaNavyBlue,
    secondaryContainer = Color(0xFF4A3A1A),
    tertiary = Color(0xFFA67C52),
    error = Color(0xFFEF5350),
    background = CribaDarkBackground,
    onBackground = CribaDarkOnBackground,
    surface = CribaDarkSurface,
    onSurface = CribaDarkOnSurface,
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color(0xFFCAC4D0),
)

private val CribaTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
)

private val CribaShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp),
)

@Composable
fun CribaTheme(
    // La app está diseñada para fondos claros; forzamos tema claro para evitar
    // texto claro sobre fondos claros (baja visibilidad) cuando el sistema está en modo oscuro.
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CribaTypography,
        shapes = CribaShapes,
        content = content
    )
}
