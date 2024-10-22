package my.destinyStudio.firetimer.screens.timerscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.destinyStudio.firetimer.data.ExpTA
import my.destinyStudio.firetimer.data.IntervalsInfo
import my.destinyStudio.firetimer.ui.theme.AppColors
import my.destinyStudio.firetimer.ui.theme.PurpleGrey40
import my.destinyStudio.firetimer.utils.formatToMMSS
import my.destinyStudio.firetimer.utils.formatToMMSSMillis

@Composable
//@Preview
fun Arcs(sizeArc: Dp =200.dp, strokeWidth:Int=10,
         valueInner:Float=0.8f, valueOuter :Float=0.4f){
    Canvas(modifier =  Modifier.size( sizeArc ) ) {
        //  val centreOfBox = Offset(size.value.width.toFloat()/2,size.value.height.toFloat()/2,)
        val outerRadius =
            (sizeArc.toPx() / 2) - (strokeWidth.toFloat() * 1f)
        val innerRadius =
            (sizeArc.toPx()  / 2) - (strokeWidth.toFloat() * 2f + 4f)
        // Draw outer arc
        drawArc(
            color = PurpleGrey40,
            startAngle = -360f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(center.x - outerRadius, center.y - outerRadius),
            size = Size( outerRadius.toDp().toPx() * 2   , outerRadius * 2),
            style = Stroke(strokeWidth.toDp().toPx(), cap = StrokeCap.Round)

        )
        drawArc(
            color = Color.Red,
            startAngle = -360f,
            sweepAngle = 355f * valueInner,
            useCenter = false,
            topLeft = Offset(center.x - outerRadius, center.y - outerRadius),
            size = Size(outerRadius * 2, outerRadius * 2),
            style = Stroke(strokeWidth.toDp().toPx(), cap = StrokeCap.Round)

        )

        // Draw inner arc
        drawArc(
            color = Color.LightGray,
            startAngle = -360f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(center.x - innerRadius, center.y - innerRadius),
            size = Size(innerRadius * 2, innerRadius * 2),
            style = Stroke(strokeWidth.toDp().toPx(), cap = StrokeCap.Round)
        )
        drawArc(
            color =  Color.Blue,
            startAngle = -360f,
            sweepAngle = 355f * valueOuter,
            useCenter = false,
            topLeft = Offset(center.x - innerRadius, center.y - innerRadius),
            size = Size(innerRadius * 2, innerRadius * 2),
            style = Stroke(strokeWidth.toDp().toPx(), cap = StrokeCap.Round)
        )


    }
}
@Preview
@Composable

fun Lines (
 strokeWidth:Dp=20 .dp,
    canvasWidth :Dp  =210.dp ,
         valueUpper:Float=0.5f,
           valueDown :Float=0.7f,
    cap: StrokeCap = StrokeCap.Round

){


    val heightA =   strokeWidth*2+10.dp

    Canvas(
            modifier = Modifier .width(canvasWidth ).height( heightA )
   ) {
   drawLine(
                color = AppColors.mLightGray,
                start = Offset(x = 0f, y = (heightA. toPx() / 4)),
                end = Offset(x = canvasWidth .toPx(), y = (heightA.toPx() / 4)),
                strokeWidth = strokeWidth.toPx(),
                cap = StrokeCap.Square,

                )

            drawLine(
                color = Color.Blue,
                start = Offset(x = 0f, y = (heightA.toPx() / 4)),
                end = Offset(x = canvasWidth .toPx() * valueDown, y = (heightA.toPx() / 4)),
                strokeWidth = strokeWidth.toPx(),
                cap = cap


            )

            drawLine(
                color = AppColors.mGray,
                start = Offset(x = 0f, y = (heightA.toPx() * 0.75).toFloat()),
                end = Offset(x = canvasWidth .toPx(), y = (heightA.toPx() * 0.75).toFloat()),
                strokeWidth = strokeWidth.toPx(),
                cap = StrokeCap.Square

            )

            drawLine(
                color = Color.Red,
                start = Offset(x = 0f, y = (heightA.toPx() * 0.75).toFloat()),
                end = Offset(x = canvasWidth .toPx() * valueUpper, y = (heightA.toPx() * 0.75).toFloat()),
                strokeWidth = strokeWidth.toPx(),
                cap = cap

            )


        }
    }



@Composable
 //@Preview(showBackground = true)
fun ExercisesIndicator (
    modifier: Modifier=Modifier
    ,index: Int=0,listOfExercise:  List  <IntervalsInfo> = ExpTA,
                        isPlus:Boolean= true,

                        ){
    val customList = listOfExercise.toMutableList()
    customList.add( 0,IntervalsInfo(intervalType = "", intervalDuration = 0, intervalName = "START !!" ))
    customList.add(customList.lastIndex+1,IntervalsInfo(intervalType = "", intervalDuration = 0, intervalName = "FINISH" ))
    customList.add(customList.lastIndex+1,IntervalsInfo(intervalType = "", intervalDuration = 0, intervalName = "" ))


//    Card(modifier = modifier  ,
//        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
//
//    ) {
        Row (modifier = modifier , verticalAlignment = Alignment.CenterVertically
             ){
            TriangleL(sizeC = 20.dp)


            Column(modifier = Modifier
                .weight(1f)
                .padding(horizontal = 2.dp)) {

        AnimatedContent( modifier =Modifier .weight(1f)
            .fillMaxWidth(),targetState = customList[index ],
           transitionSpec = {  slideInVertically { if (isPlus) it else if(!isPlus) -it else it } togetherWith slideOutVertically { if (isPlus) -it else if(!isPlus) it else -it  } }

                        , label = "") {
                            interval  -> IntervalIndicator(index, interval )
                    }

                Card(modifier = Modifier
                    .height(3.dp)
                    .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.Green)
                ){}

                    AnimatedContent(modifier =Modifier .weight(1f)
                        .fillMaxWidth(), targetState = customList[ index+1 ],
                        transitionSpec = {
                            slideInVertically { if (isPlus) it else if(!isPlus) -it else it } togetherWith slideOutVertically { if (isPlus) -it else if(!isPlus) it else -it  }

                        }, label = "") {
                            interval  -> IntervalIndicator( index+1 ,interval   )
                    }

                   Card(modifier = Modifier
                       .height(3.dp)
                       .fillMaxWidth(),
                       colors = CardDefaults.cardColors(containerColor = Color.Green)
                        ){}
                    AnimatedContent(modifier =Modifier .weight(1f)
                        .fillMaxWidth(), targetState = customList[index+2],
                        transitionSpec = { slideInVertically { if (isPlus) it else if(!isPlus) -it else it } togetherWith slideOutVertically { if (isPlus) -it else if(!isPlus) it else -it  }

                        }, label = "") {
                            interval  -> IntervalIndicator( index+2 ,interval  )
                    }

            }

            TriangleR(sizeC = 20.dp)

        }


   // }
}
@Composable
 //@Preview
fun IntervalIndicator(
    index : Int = 0 ,
     interval : IntervalsInfo ,
    intervalIndicatorHeight:Dp =30.dp

){
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 2.dp)
         .height(intervalIndicatorHeight)

    ) {

   Row(
       Modifier
           .background(Color.Red)
           .fillMaxSize()  ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
   ){

  Text(modifier = Modifier.weight(0.4f),text =
                       if (interval.intervalName=="START !!"||interval.intervalName=="FINISH") ""
                       else if (interval.intervalName=="" && interval.intervalName=="" && interval.intervalDuration==0L )""
                       else(index ).toString()
      ,
      textAlign = TextAlign.Center  , color = Color.White  , overflow = TextOverflow.Ellipsis, maxLines = 1, fontSize = 20.sp)

    Text(modifier = Modifier.weight(1f),
        text = interval.intervalName.ifEmpty { interval.intervalType }
               , textAlign = TextAlign.Center, color = Color.White , overflow = TextOverflow.Ellipsis, maxLines = 1 , fontSize = 20.sp  )

   Text(modifier = Modifier.weight(0.4f),
           text  = if(interval.intervalDuration==0L) ""
           else if (interval.intervalName=="" && interval.intervalName==""&&interval.intervalDuration==0L )""
                   else  formatToMMSS( interval.intervalDuration )
       , textAlign = TextAlign.Center  ,color = Color.White, overflow = TextOverflow.Ellipsis, maxLines = 1 , fontSize = 20.sp)
    }
}

}
//@Preview
@Composable

fun TriangleL(
    modifier: Modifier = Modifier,
    sizeC: Dp=100.dp,
    color: Color = Color.Green,

) {
    Canvas(
        modifier = modifier.size(sizeC)
    ) {
        val path = Path().apply {
            moveTo(0f   , 0f) // Move to the top-center
            lineTo(size.width, size.height/2) // Draw a line to the bottom-left
            lineTo(0f, size.height) // Draw a line to the bottom-right
            close() // Close the path to complete the triangle
        }

        drawPath(
            path = path,
            color = color
        )
    }
}
//@Preview
@Composable

fun TriangleR(
    modifier: Modifier = Modifier,
    sizeC: Dp=100.dp,
    color: Color = Color.Green,


) {
    Canvas(
        modifier = modifier.size(sizeC)
    ) {
        val path = Path().apply {
            moveTo(size.width    , 0f) // Move to the top-center
            lineTo(0f, size.width/2) // Draw a line to the bottom-left
            lineTo(size.width    , size.height) // Draw a line to the bottom-right
            close() // Close the path to complete the triangle
        }

        drawPath(
            path = path,
            color = color
        )
    }
}

//@Preview
@Composable

fun TimeText(modifier: Modifier=Modifier,

    timeLeft :Long = 20000L,
    textSize :Int=   35 ,
    color: Color = Color.Green 

){

        Text(modifier=modifier,text = formatToMMSSMillis(timeLeft), color = color, fontSize = textSize.sp)



}