@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package my.destinyStudio.firetimer.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.destinyStudio.firetimer.R
import my.destinyStudio.firetimer.ui.theme.dimens

@Preview
@Composable
fun Intervals(modifier: Modifier=Modifier  ,

              imageId : Int = R.drawable.cool_down,
              pDuration:Long = 10000L,
              intervalType:String="Warmup",
              secondDurationValue : (Long) -> Unit={}
) {

   var secondDuration by remember { mutableLongStateOf(0) }

    Card(modifier = modifier
       ,
          colors = CardDefaults.cardColors(
            containerColor = Color.Transparent

        ),
        shape = RoundedCornerShape(size = 15.dp),
       border = BorderStroke(width = 1.dp, color = Color.LightGray),


    ) {

        Row(modifier = Modifier.padding(1.dp),

            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {


  Spacer(Modifier.width(20.dp).background(Color.Transparent))

   Image(modifier = Modifier
       .fillMaxHeight().padding(start = 5.dp, top = 3.dp, bottom = 3.dp)
       .width(90.dp),painter = painterResource(id = imageId), contentDescription = ""   )



  Column(modifier=Modifier.fillMaxWidth() ,verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {


                    Text(modifier = Modifier.padding(0.dp),
                        text = " $intervalType ",  fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.headlineMedium ,
                        fontSize = MaterialTheme.dimens.appTitleInfo.sp,
                        lineHeight = 5 .sp

                    )

     TimePickerCard(
         modifier=Modifier.padding(bottom = 3.dp, top = 3.dp)
         ,minusColor = Color.Blue, plusColor = Color.Red,
         secondsInPut = (pDuration.toInt() /1000)%60,
         minutesInPut =  (pDuration.toInt() /1000)/60  ) { duration ->
                    secondDuration = duration

                }
            }

                }
    }
    secondDurationValue(secondDuration)
}






