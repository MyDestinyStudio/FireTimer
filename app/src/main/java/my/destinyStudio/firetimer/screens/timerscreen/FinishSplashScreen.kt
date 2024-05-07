package my.destinyStudio.firetimer.screens.timerscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
//@Preview
fun FinishSplashScreen  (){
    Surface(Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .background(
                Color.Blue
            //    Brush.horizontalGradient(
//          listOf(AppColors.mBlueD,AppColors.mBlueL,AppColors.mOrangeL
//            ,AppColors.mOrangeD))
            ),

            contentAlignment = Alignment.Center
            ) {
             Text(text = "1", fontSize = 250.sp, color = Color.White)
        }

    }


}
@Composable
@Preview
fun  SplashDialog (onDismiss:()->Unit={}){
    Dialog(onDismissRequest = {  onDismiss() }) {
       Card( Modifier ) {
            Box(
                modifier = Modifier
                     .height(200.dp)
                    .background(
                        Color.Green
//                        Brush.horizontalGradient(
//                            listOf(
//                                AppColors.mBlueD,
//                                AppColors.mBlueL,
//                                AppColors.mOrangeL,
//                                AppColors.mOrangeD
//                            )
//                        )
                    ),

                contentAlignment = Alignment.Center
            ) {
                Text(modifier = Modifier.padding(5.dp),
                    text = "YOU HAVE FINISHED YOUR WORKOUT CHAAAAAAMP",
                    fontSize = 30.sp, color = Color.White
                )
            }
        }

        
    }

}
