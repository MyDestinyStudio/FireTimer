package my.destinyStudio.firetimer.ui.theme

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import my.destinyStudio.firetimer.MainActivity

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
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun FireTimerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    activity: Activity= LocalContext.current as MainActivity,
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }


     val window = calculateWindowSizeClass(activity=activity)
    val config = LocalConfiguration.current
    var appDimens = CompactDimens

    when (config.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            when(window.widthSizeClass){
                WindowWidthSizeClass.Compact-> {
                    appDimens = if(config.screenWidthDp <= 390){

                        CompactSmallDimens

                    } else if(config.screenWidthDp<590){
                        CompactMediumDimens
                    } else{
                        CompactDimens
                    }
                }

                WindowWidthSizeClass.Medium ->{
                    appDimens= MediumDimens
                }

                WindowWidthSizeClass.Expanded ->{
                    appDimens= ExpandedDimens
                }

            }
            Log.d("AppDim","Portrait ${window.widthSizeClass}    $appDimens")
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
            when(window.heightSizeClass){
                WindowHeightSizeClass.Compact -> {
                    appDimens =
                        CompactDimens

                }

                WindowHeightSizeClass.Medium ->{
                    appDimens= MediumDimens
                }

                WindowHeightSizeClass.Expanded ->{
                    appDimens= ExpandedDimens
                }
            }
            Log.d("AppDim"," Landscape ${window.heightSizeClass} $appDimens")


        }
        else -> {
            when(window.widthSizeClass){
                WindowWidthSizeClass.Compact-> {
                    appDimens = if(config.screenWidthDp <= 390){
                        CompactSmallDimens
                    } else if(config.screenWidthDp<590){
                        CompactMediumDimens
                    } else{
                        CompactDimens
                    }
                }

                WindowWidthSizeClass.Medium ->{
                    appDimens= MediumDimens
                }

                WindowWidthSizeClass.Expanded ->{
                    appDimens= ExpandedDimens
                }

            }
        }
    }

AppUtils(appDimens = appDimens) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
    
}

}
val MaterialTheme.dimens
@Composable
get() = LocalAppDimens.current
