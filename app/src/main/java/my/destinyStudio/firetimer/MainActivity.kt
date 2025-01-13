package my.destinyStudio.firetimer


import android.os.Build.VERSION_CODES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import my.destinyStudio.firetimer.navigation.NavigationOperation
import my.destinyStudio.firetimer.ui.theme.FireTimerTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  //  private val REQUEST_CODE = 123

    @RequiresApi(VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            FireTimerTheme {

//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
//                    != PackageManager.PERMISSION_GRANTED
//                ) {
//                    // Request permissions
//                    ActivityCompat.requestPermissions(
//                        this,
//                        arrayOf(Manifest.permission.READ_PHONE_STATE ),
//                        REQUEST_CODE
//                    )
//                } else {
//                    // Permissions already granted, proceed with your logic
//                }

               NavigationOperation( )


        }
            }
              }



    }



