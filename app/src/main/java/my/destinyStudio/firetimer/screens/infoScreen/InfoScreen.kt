package my.destinyStudio.firetimer.screens.infoScreen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import my.destinyStudio.firetimer.screens.savedworkouts.TopAppBarA


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun InfoScreen(navController: NavController
               = NavController(context = LocalContext.current)) {

    val configuration = LocalConfiguration.current
    BackHandler {
        navController.popBackStack()
    }
    Scaffold(modifier = Modifier.fillMaxSize()
        , topBar = {
            when {
                configuration.orientation== Configuration.ORIENTATION_LANDSCAPE &&configuration.screenHeightDp<480 -> {

                }
                else -> {
            TopAppBarA  (
                title = "Information Screen"
            )

                }}}
    ) {

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier, text = "Beg for Help",
                color = Color.Green, fontSize = 35.sp
            )
        }

    }

}