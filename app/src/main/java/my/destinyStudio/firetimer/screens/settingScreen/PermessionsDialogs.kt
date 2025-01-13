@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package my.destinyStudio.firetimer.screens.settingScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Preview
@Composable
fun PermissionDialog(
    modifier: Modifier = Modifier,
    permissionTextProvider: PermissionTextProvider = PhoneCallPermissionTextProvider()    ,
    isPermanentlyDeclined: Boolean=true,
    onDismiss: () -> Unit={},
    onOkClick: () -> Unit={},
    onGoToAppSettingsClick: () -> Unit={},

) {
    Dialog(onDismissRequest = {onDismiss()}) {

       Surface(shape = RoundedCornerShape(18.dp), color = Color.LightGray) {
           Column(
               modifier = Modifier
                   .fillMaxWidth()
, horizontalAlignment = Alignment.CenterHorizontally
           ) {

       Text(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
      text =  permissionTextProvider.getDescription( isPermanentlyDeclined = isPermanentlyDeclined )
            )


               HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp), thickness = 4.dp)
               Button(modifier =Modifier.fillMaxWidth(0.8f), onClick = {
                   if (isPermanentlyDeclined) {
                       onGoToAppSettingsClick()
                   } else {
                       onOkClick()
                   }
               }) {
                   Text(
                       text = if (isPermanentlyDeclined) {
                           "Grant permission"
                       } else {
                           "OK"
                       },
                       fontWeight = FontWeight.Bold,
                       textAlign = TextAlign.Center,
                       modifier = Modifier
                           .fillMaxWidth()

                           .padding(16.dp)
                   )
               }
           }

        }



    }


    }

interface PermissionTextProvider  {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

//class CameraPermissionTextProvider: PermissionTextProvider {
//    override fun getDescription(isPermanentlyDeclined: Boolean): String {
//        return if(isPermanentlyDeclined) {
//            "It seems you permanently declined camera permission. " +
//                    "You can go to the app settings to grant it."
//        } else {
//            "This app needs access to your camera so that your friends " +
//                    "can see you in a call."
//        }
//    }
//}
//
//class RecordAudioPermissionTextProvider: PermissionTextProvider {
//    override fun getDescription(isPermanentlyDeclined: Boolean): String {
//        return if(isPermanentlyDeclined) {
//            "It seems you permanently declined microphone permission. " +
//                    "You can go to the app settings to grant it."
//        } else {
//            "This app needs access to your microphone so that your friends " +
//                    "can hear you in a call."
//        }
//    }
//}

class PhoneCallPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "It seems you permanently declined phone calling permission. " +
                    "You can go to the app settings to grant it."
        } else {
            "FireTimer needs phone calling permission so that the timer will be paused on incoming calls"
        }
    }
}