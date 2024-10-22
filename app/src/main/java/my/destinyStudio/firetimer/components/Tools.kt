package my.destinyStudio.firetimer.components



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified


@Composable
fun AutoText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.labelSmall ,
     color: Color  =Color.White
) {
    var resizedTextStyle by remember {
        mutableStateOf(style)
    }
    var shouldDraw by remember {
        mutableStateOf(false)
    }
    val defaultFontSize = MaterialTheme.typography.labelSmall.fontSize


    Text(
        text = text,
        color = color,
        modifier = modifier.drawWithContent {
            if (shouldDraw) {
                drawContent()
            }
        },
        softWrap = false,
        style = resizedTextStyle,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                if (style.fontSize.isUnspecified) {
                    resizedTextStyle = resizedTextStyle.copy(
                        fontSize = defaultFontSize
                    )
                }
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )
            } else {
                shouldDraw = true
            }
        }
    )
}
//@Preview
@Composable
fun A() {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

            AutoText(
                text = "qwdQAasasasAAAAAAAAAAAAAAAAAAAAA",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White
            )

        Row {

                AutoText(
                    text = "q",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White
                )


                AutoText(
                    text = "ql",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White
                )


        }
    }
} 
@Composable

fun windowDimensions() :WindowInfo{
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWidthInfo = when {
            configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
            configuration.screenWidthDp < 840 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenHeightInfo = when {
            configuration.screenWidthDp <480 -> WindowInfo.WindowType.Compact
            configuration.screenWidthDp <  900 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp


    )
}

data class WindowInfo(
    val screenWidthInfo : WindowType,
    val screenHeightInfo : WindowType,
    val screenWidth: Dp,
    val screenHeight :Dp


    ){
    sealed class WindowType{
        data object Compact : WindowType()
        data object Medium : WindowType()
        data object Expanded : WindowType()

    }
}


