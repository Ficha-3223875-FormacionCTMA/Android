package com.esteban.miformacionctma.ui.theme

<<<<<<< HEAD
import android.app.Activity
=======
>>>>>>> dbc20b7 (actualizacion de proyecto)
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
<<<<<<< HEAD
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
=======
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = AzulPrimario,
    onPrimary = OnAzulPrimario,
    primaryContainer = AzulContainerClaro,
    onPrimaryContainer = OnAzulContainerClaro,
    secondary = TealSecundario,
    onSecondary = Color.White,
    secondaryContainer = TealContainerClaro,
    onSecondaryContainer = OnTealContainerClaro,
    tertiary = VioletaTerciario,
    onTertiary = Color.White,
    tertiaryContainer = VioletaContainerClaro,
    onTertiaryContainer = OnVioletaContainerClaro,
    background = FondoClaro,
    onBackground = OnSuperficieClaro,
    surface = SuperficieClara,
    onSurface = OnSuperficieClaro,
    surfaceVariant = SuperficieVariantClaro,
    onSurfaceVariant = OnSuperficieVariantClaro,
    outline = ContornoClaro,
    outlineVariant = SuperficieVariantClaro,
    error = ErrorClaro,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = AzulContainerClaro,
    onPrimary = OnAzulContainerClaro,
    primaryContainer = AzulContainerOscuro,
    onPrimaryContainer = OnAzulContainerOscuro,
    secondary = TealContainerClaro,
    onSecondary = OnTealContainerClaro,
    secondaryContainer = TealContainerOscuro,
    onSecondaryContainer = OnTealContainerOscuro,
    tertiary = VioletaContainerClaro,
    onTertiary = OnVioletaContainerClaro,
    tertiaryContainer = VioletaContainerOscuro,
    onTertiaryContainer = OnVioletaContainerOscuro,
    background = FondoOscuro,
    onBackground = OnSuperficieOscuro,
    surface = SuperficieOscura,
    onSurface = OnSuperficieOscuro,
    surfaceVariant = SuperficieVariantOscuro,
    onSurfaceVariant = OnSuperficieVariantOscuro,
    outline = ContornoOscuro,
    outlineVariant = SuperficieVariantOscuro,
    error = ErrorOscuro,
    onError = Color(0xFF690005)
>>>>>>> dbc20b7 (actualizacion de proyecto)
)

@Composable
fun MiFormacionCTMATheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
<<<<<<< HEAD
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
=======
    dynamicColor: Boolean = false,
>>>>>>> dbc20b7 (actualizacion de proyecto)
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}