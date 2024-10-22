package my.destinyStudio.firetimer

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import my.destinyStudio.firetimer.navigation.NavigationOperation
import my.destinyStudio.firetimer.ui.theme.FireTimerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

 @RequiresApi(Build.VERSION_CODES.R)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    setContent {
            FireTimerTheme {     NavigationOperation()  }
              }


    }

}

