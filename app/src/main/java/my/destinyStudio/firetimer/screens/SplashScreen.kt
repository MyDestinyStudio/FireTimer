package my.destinyStudio.firetimer.screens


import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import my.destinyStudio.firetimer.navigation.SplashScreen
import my.destinyStudio.firetimer.navigation.StartScreen
import my.destinyStudio.firetimer.screens.settingScreen.SettingsViewModel


private const val PADDING_PERCENTAGE_OUTER_CIRCLE = 0.15f
private const val PADDING_PERCENTAGE_INNER_CIRCLE = 0.3f
private const val POSITION_START_OFFSET_OUTER_CIRCLE = 90f
private const val POSITION_START_OFFSET_INNER_CIRCLE = 135f




@Composable
fun SplashScreen(  navController: NavController = NavController(context =   LocalContext.current),
                 settingViewModel: SettingsViewModel) {

    val settingsLoaded by settingViewModel.settingsLoaded.collectAsState( )
    LaunchedEffect (key1 = settingsLoaded){
     kotlinx.coroutines.delay(1000)

        navController.navigate( StartScreen,
              navOptions { popUpTo(SplashScreen){inclusive=true} }
            )
     Log.d("StartScreen","launched")

    }

    Surface(modifier = Modifier.fillMaxSize() ) {


        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ) {
            TripleOrbitLoadingAnimation()
        }
    }

}
@Preview
@Composable
fun TripleOrbitLoadingAnimation(modifier: Modifier = Modifier) {


    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 750),
        ),
        label = "rotation animation"
    )

    var width by remember {
        mutableIntStateOf(0)
    }

    Box(
        modifier = modifier
            .size(200.dp)
            .onSizeChanged {
                width = it.width
            },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeWidth = 5.dp,
            color = Color.Yellow,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationZ = rotation
                }
        )
        CircularProgressIndicator(
            strokeWidth = 5.dp,
            color = Color.Blue,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    with(LocalDensity.current) {
                        (width * PADDING_PERCENTAGE_INNER_CIRCLE).toDp()
                    }
                )
                .graphicsLayer {
                    rotationZ = rotation + POSITION_START_OFFSET_INNER_CIRCLE
                }
        )
        CircularProgressIndicator(
            strokeWidth = 5.dp,
            color = Color.Red,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    with(LocalDensity.current) {
                        (width * PADDING_PERCENTAGE_OUTER_CIRCLE).toDp()
                    }
                )
                .graphicsLayer {
                    rotationZ = rotation + POSITION_START_OFFSET_OUTER_CIRCLE
                }
        )
    }
}