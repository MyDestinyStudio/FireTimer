@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package my.destinyStudio.firetimer.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import my.destinyStudio.firetimer.R

@Preview
@Composable
fun Intervals(modifier: Modifier=Modifier  ,

              imageId : Int = R.drawable.cool_down,
              pDuration:Long = 10000L,
              intervalType:String="Warmup",
              secondDurationValue : (Long) -> Unit={}
) {

   var secondDuration by remember { mutableLongStateOf(0) }

    Card(modifier = modifier.height(140.dp)
       ,
          colors = CardDefaults.cardColors(
            containerColor = Color.Transparent

        ),
        shape = RoundedCornerShape(size = 15.dp),
       border = BorderStroke(width = 2.dp, color = Color.LightGray),


    ) {

        ConstraintLayout  (modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp, vertical = 2.dp) ,

            ) {

            val (image, text,  picker ) = createRefs()



   Image(modifier =
   Modifier.constrainAs(image) {
       start.linkTo(parent.start )
       top.linkTo( parent.top)
       bottom.linkTo( parent.bottom)
       end.linkTo(picker.start)

   }.size(48.dp)  ,
       painter = painterResource(id = imageId), contentDescription = ""   )

   Text(modifier = Modifier.constrainAs(text) {
       start.linkTo(image.end )
       top.linkTo( parent.top)
       bottom.linkTo( picker.top)
       end.linkTo(parent.end)

   } , text = " $intervalType ",
       fontWeight = FontWeight.Light,
     style = MaterialTheme.typography.titleSmall ,
     //   fontSize = MaterialTheme.dimens.appTitleInfo.sp,
       lineHeight = 5 .sp
  )

            Box(modifier=Modifier .constrainAs(picker) {
                start.linkTo(image.end )
                top.linkTo( text.bottom)
                bottom.linkTo( parent.bottom)
                end.linkTo(parent.end)

            }) {
                TimePickerCard(
 minusColor = Color.Blue, plusColor = Color.Red,
                    secondsInPut = (pDuration.toInt() / 1000) % 60,
                    minutesInPut = (pDuration.toInt() / 1000) / 60
                ) { duration ->
                    secondDuration = duration

                }
            }
            }

                }
    secondDurationValue(secondDuration)
    }








