package my.destinyStudio.firetimer.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//
//@Composable
//
//fun ScreenDimenions() :WindowInfo{
//    val configuration = LocalConfiguration.current
//    return WindowInfo(
//        screenWidthInfo = when {
//            configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
//            configuration.screenWidthDp < 840 -> WindowInfo.WindowType.Medium
//            else -> WindowInfo.WindowType.Expanded
//        },
//        screenHeightInfo = when {
//            configuration.screenWidthDp <480 -> WindowInfo.WindowType.Compact
//            configuration.screenWidthDp <  900 -> WindowInfo.WindowType.Medium
//            else -> WindowInfo.WindowType.Expanded
//        },
//        screenWidth = configuration.screenWidthDp.dp,
//        screenHeight = configuration.screenHeightDp.dp
//
//
//    )
//}
//
//data class ScreenDimensAndOrientation ()
//    {
//    sealed class WindowType{
//        data object Compact : WindowType()
//        data object Medium : WindowType()
//        data object Expanded : WindowType()
//
//    }
//}

