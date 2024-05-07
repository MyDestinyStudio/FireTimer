@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package my.destinyStudio.firetimer.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun Intervals(imageId : Int =  3, pDuration:Long = 10000L, intervalType:String="Warmup", secondDurationValue : (Long) -> Unit={}) {

   var secondDuration by remember { mutableLongStateOf(0) }

    Card(modifier = Modifier  .padding(2.dp)
        .fillMaxSize()
       ,
          colors = CardDefaults.cardColors(
            containerColor = Color.Transparent

        ),
        shape = RoundedCornerShape(size = 15.dp),
       border = BorderStroke(width = 1.dp, color = Color.LightGray),


    ) {

        Row(modifier = Modifier.padding(1.dp),

            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
//Image Box

  Box(modifier = Modifier
                    .fillMaxHeight()
                    .width(90.dp)
                     , contentAlignment = Alignment.Center
                ){
    Icon(imageVector =Icons.Filled.Person , contentDescription = "",
                        modifier= Modifier
                            .fillMaxHeight()
                            .fillMaxWidth())

                }

  Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

      Box(modifier =  Modifier.fillMaxWidth(),contentAlignment = Alignment.Center){
                    Text(text = " $intervalType ",  fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.headlineMedium ,
                        fontSize = 20.sp)
                }
     TimePickerCard(minusColor = Color.Blue, plusColor = Color.Red,
         secondsInPut = (pDuration.toInt() /1000)%60,
         minutesInPut =  (pDuration.toInt() /1000)/60  ) { duration ->
                    secondDuration = duration

                }
            }

                }
    }
    secondDurationValue(secondDuration)
}






