package my.destinyStudio.firetimer.screens.timerscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.rounded.KeyboardDoubleArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@RequiresApi(Build.VERSION_CODES.O)
@Composable
 //@Preview
fun FloatingTimerTest(

                  innerL:Float=0.9f,
                  outerL:Float=0.6f,
                  timeLeft:Long ,
                  //timeLeft:Long=2000,
                //  onLeftClick: () -> Unit={},
                //  onRightClick: () -> Unit={}
){
    //val i by remember { mutableIntStateOf(3) }



  //  Column(modifier = Modifier.background(Color.Transparent)  ) {
        BoxWithConstraints   (contentAlignment = Alignment.Center){
            Arcs(
                sizeArc = this.maxWidth  ,
                strokeWidth = 20,
                valueInner = innerL,
                valueOuter = outerL
            )
            TimeText(
                timeLeft = timeLeft,
                textSize = 15

            )
        }
//Row(modifier = Modifier.fillMaxWidth(),
//    horizontalArrangement = Arrangement.Center,
//    verticalAlignment = Alignment.CenterVertically) {
//    IconButton(
//        modifier = Modifier.size(24.dp),
//        onClick = {
//            onLeftClick()
//                  },
//        enabled = i != 0
//    ) {
//        Icon(
//
//            imageVector = Icons.Rounded.KeyboardDoubleArrowLeft,
//            tint = Color.Yellow,
//            contentDescription = ""
//        )
//    }
//
//
//    ExercisesIndicator(modifier = Modifier.weight(1f).height(35.dp ) , visible = false)
//
//    IconButton(
//        modifier = Modifier.size(24.dp),
//        onClick = {
//            onRightClick()
//        },
//       enabled =  i != 0  ,
//
//        ) {
//        Icon(
//           modifier = Modifier.size(24.dp),
//            imageVector = Icons.Rounded.KeyboardDoubleArrowRight,
//            tint = Color.Yellow,
//            contentDescription = ""
//        )
//
//    }
//}

  //  }


         }








@Composable
@Preview
fun NotificationTimer(
    //timerAViewModel: TimerAViewModel = hiltViewModel(),
    innerL:Float=0.9f,
    outerL:Float=0.6f
){
    val i by remember { mutableIntStateOf(3) }



    val isLocked by rememberSaveable { mutableStateOf(false) }
   // val context = LocalContext.current

   Column(modifier = Modifier.fillMaxWidth()) {
                BoxWithConstraints {

                    Lines(
                        strokeWidth = 7.dp,
                        canvasWidth = this.maxWidth,
                        valueDown = outerL,
                        valueUpper = innerL,

                        )
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = {

                        },
                        enabled = i != 0 && !isLocked
                    ) {
                        Icon(

                            imageVector = Icons.Rounded.KeyboardDoubleArrowLeft,
                            tint = Color.Yellow,
                            contentDescription = ""
                        )
                    }


                    ExercisesIndicator(modifier = Modifier.height(30.dp).weight(1f) , visible = false)

                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = {
                            // timerAViewModel.nextInterval()
                        },
                        enabled = !isLocked,

                        ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Rounded.KeyboardDoubleArrowRight,
                            tint = Color.Yellow,
                            contentDescription = ""
                        )

                    }
                }

            }





        }






