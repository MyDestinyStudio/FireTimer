package my.destinyStudio.firetimer.screens.infoScreen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    BackHandler {
        navController.popBackStack()
    }
    Scaffold(modifier = Modifier.fillMaxSize()
        , topBar = {
            TopAppBarA  (
                title = "Information Screen"
            )

        }
    ) {

        Text(modifier = Modifier ,text = "Beg for Help",
            color = Color.Green, fontSize = 35.sp)

    }

}