package ink.flybird.anifly.ui.theme


import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ink.flybird.anifly.data.common.AFSetting
import ink.flybird.anifly.ui.theme.palette.LocalTonalPalettes
import ink.flybird.anifly.ui.theme.palette.TonalPalettes
import ink.flybird.anifly.ui.theme.palette.core.ProvideZcamViewingConditions
import ink.flybird.anifly.ui.theme.palette.dynamic.extractTonalPalettesFromUserWallpaper
import ink.flybird.anifly.ui.theme.palette.dynamicDarkColorScheme
import ink.flybird.anifly.ui.theme.palette.dynamicLightColorScheme

@Composable
fun AppTheme(
    useDarkTheme: Boolean,
    wallpaperPalettes: List<TonalPalettes> = extractTonalPalettesFromUserWallpaper(),
    content: @Composable () -> Unit,
) {
    val themeIndex = AFSetting.themeIndex

    val tonalPalettes = wallpaperPalettes[
            if (themeIndex >= wallpaperPalettes.size) {
                when {
                    wallpaperPalettes.size == 5 -> 0
                    wallpaperPalettes.size > 5 -> 5
                    else -> 0
                }
            } else {
                themeIndex
            }
    ]

    ProvideZcamViewingConditions {
        CompositionLocalProvider(
            LocalTonalPalettes provides tonalPalettes.apply { Preparing() },
            LocalTextStyle provides LocalTextStyle.current.applyTextDirection()
        ) {
            MaterialTheme(
                colorScheme =
                if (useDarkTheme) dynamicDarkColorScheme()
                else dynamicLightColorScheme(),
                shapes = Shapes,
                content = content,
            )
        }
    }
}
