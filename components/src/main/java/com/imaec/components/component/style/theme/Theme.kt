import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightThemeColors = lightColors(
    primary = Primary500,
    secondary = Gray33,
    error = Red900
)

@Composable
fun HlTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = LightThemeColors,
        typography = Typography(),
        content = content
    )
}

enum class Theme{
    LIGHT_THEME,
    DARK_THEME,
}
