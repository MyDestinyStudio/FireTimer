package my.destinyStudio.firetimer.screens.settingScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.destinyStudio.firetimer.components.TimePickerCard
import my.destinyStudio.firetimer.ui.theme.dimens


data class DefaultWorkoutParameter(
    var warmUpDuration  : Long  =2000,
    var workOutDuration:Long  =2000,
    var restDuration:Long  =2000,
    var restBtwSetsDuration:Long  =2000,
    var cooldownDuration:Long  =2000,
    var cycleNumbers:Int =2,
    var setsNumbers:Int =3,
)
@Preview
@Composable
fun DefaultWorkoutEditor(modifier: Modifier=Modifier,
     warmUpDurationP  :Long =100 ,
     workOutDurationP  :Long =200,
     restDurationP :Long=180,
      restBtwSetsDurationP :Long =120  ,
     cooldownDurationP  :Long =180 ,
     cycleNumbersP  :Int =2,
     setsNumbersP:Int =3,
     parameterRetriever : (DefaultWorkoutParameter) -> Unit={}
){


    var warmUpDuration by rememberSaveable { mutableLongStateOf(warmUpDurationP) }
    var workOutDuration by rememberSaveable { mutableLongStateOf(workOutDurationP) }
    var restDuration by rememberSaveable {mutableLongStateOf(restDurationP) }
    var restBtwSetsDuration by rememberSaveable { mutableLongStateOf(restBtwSetsDurationP) }
    var cooldownDuration by rememberSaveable { mutableLongStateOf(cooldownDurationP) }
    var cycleNumbers by rememberSaveable { mutableIntStateOf(cycleNumbersP) }
    var setsNumbers by rememberSaveable { mutableIntStateOf(setsNumbersP) }

    parameterRetriever(
        DefaultWorkoutParameter(
        warmUpDuration = warmUpDuration ,
        workOutDuration = workOutDuration,
            restDuration = restDuration,
            restBtwSetsDuration = restBtwSetsDuration,
            cooldownDuration = cooldownDuration,
            cycleNumbers = cycleNumbers,
            setsNumbers = setsNumbers
    )
    )

    Card (modifier =modifier

        , colors = CardDefaults.cardColors(containerColor = Color.Red)) {
        Text(modifier = Modifier.padding(start = 12.dp), text = "Default Workout : ", style = MaterialTheme.typography.headlineMedium, color = Color.White)

        Row(modifier = Modifier.padding(vertical = 2.dp, horizontal = 5.dp ),verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.padding(horizontal = 10.dp).weight(1f) , text = "Warm up", color = Color.White)

   TimePickerCard(minutesInPut = warmUpDurationP.toInt()/60, secondsInPut = warmUpDurationP.toInt()%60,  minusColor =  Color.White, plusColor = Color.White,
       borderColor = Color.White, color = Color.White){ warmUpDuration=it   }}

    Row(modifier = Modifier.padding(vertical = 2.dp, horizontal = 5.dp),verticalAlignment = Alignment.CenterVertically)  {
        Text(modifier = Modifier.padding(horizontal = 10.dp).weight(1f) ,text = "Work out", color = Color.White)
 TimePickerCard(minutesInPut = (workOutDuration/60).toInt(), secondsInPut = (workOutDuration%60).toInt(), minusColor = Color.White, plusColor = Color.White,
     borderColor = Color.White, color = Color.White){ workOutDuration=it    } }

    Row(modifier = Modifier.padding(vertical = 2.dp, horizontal = 5.dp),verticalAlignment = Alignment.CenterVertically)  {
  Text(modifier = Modifier.padding(horizontal = 10.dp).weight(1f) ,text = "Rest", color = Color.White)
 TimePickerCard(minutesInPut = (restDuration/60).toInt(), secondsInPut = (restDuration%60).toInt(), minusColor = Color.White, plusColor = Color.White,
     borderColor = Color.White, color = Color.White){ restDuration=it   }
    }


    Row(modifier = Modifier.padding(vertical = 2.dp, horizontal = 5.dp ), verticalAlignment = Alignment.CenterVertically)  {
 Text(modifier = Modifier.padding(horizontal = 10.dp).weight(1f) ,text = "Rest Btw Sets" , color = Color.White )
  TimePickerCard(minutesInPut = (restBtwSetsDuration/60).toInt(), secondsInPut = (restBtwSetsDuration%60).toInt(), minusColor = Color.White, plusColor =Color.White,
      borderColor = Color.White, color = Color.White){ restBtwSetsDuration=it   }
    }



        Row(modifier = Modifier.padding(vertical = 2.dp, horizontal = 5.dp),verticalAlignment = Alignment.CenterVertically)  {
        Text(modifier = Modifier.padding(horizontal = 10.dp).weight(1f) ,text = "Cool Down" , color = Color.White )
 TimePickerCard(modifier = Modifier.padding(end = 5.dp ), minutesInPut = (cooldownDuration/60).toInt(), secondsInPut = (cooldownDuration%60).toInt(), minusColor = Color.White, plusColor = Color.White,
     borderColor = Color.White, color = Color.White){cooldownDuration=it  }
    }


        Row(
            modifier = Modifier.padding(top = 3.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.padding(horizontal = 10.dp).weight(1/6f),text = "Cycles" , color = Color.White )
            IconButton(modifier = Modifier.padding(horizontal = 10.dp).weight(0.2f),onClick = {
                cycleNumbers = if (cycleNumbers > 1) cycleNumbers - 1 else 1
            }, colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "" )
            }

            Text(
                modifier = Modifier.weight(1/6f)
                  //  .padding(horizontal = 9.dp ),

                ,text = "$cycleNumbers",
                fontSize = MaterialTheme.dimens.pickerCardSFontSize.sp, color = Color.White,
                textAlign = TextAlign.Center
            )
            IconButton(modifier = Modifier.padding(horizontal = 10.dp).weight(1/6f),onClick = {  cycleNumbers += 1 }, colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }


        }

        Row(
            modifier = Modifier.padding(top = 3.dp, bottom = 5.dp ),
            //horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
  
            Text(modifier = Modifier.padding(horizontal = 10.dp).weight(1/6f),text = "Sets  ", color = Color.White )
            IconButton(modifier = Modifier.padding(horizontal = 10.dp).weight(1/6f),onClick = {
                setsNumbers -= 1
            }, colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "")
            }

            Text(
                modifier = Modifier.weight(1/6f)
                   // .padding(horizontal = 9.dp ),
                // .align(Alignment.CenterVertically),
               , text = "$setsNumbers",
                fontSize = MaterialTheme.dimens.pickerCardSFontSize.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            IconButton(modifier = Modifier.padding(horizontal = 10.dp).weight(1/6f),onClick = { setsNumbers += 1 }, colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }


        }

    }
}

