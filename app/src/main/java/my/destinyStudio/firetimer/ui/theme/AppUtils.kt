package my.destinyStudio.firetimer.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun AppUtils(
    appDimens: DimensApp,
    content: @Composable ()-> Unit
){
    val appDimension = remember{appDimens}
    CompositionLocalProvider(value = LocalAppDimens provides  appDimension) {
        content()
        
    }


}
val LocalAppDimens = compositionLocalOf {
    CompactDimens
}
val ScreenOrientation
@Composable
get() = LocalConfiguration.current.orientation