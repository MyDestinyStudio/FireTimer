package my.destinyStudio.firetimer.screens.settingScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.destinyStudio.firetimer.data.IntervalsInfo
import my.destinyStudio.firetimer.data.IntervalsInfoIndexed
import my.destinyStudio.firetimer.screens.editworkout.IntervalModifier

@Preview
@Composable
fun  DefaultIntervalPicker(
     modifier: Modifier = Modifier,
     interval  : IntervalsInfoIndexed  = IntervalsInfoIndexed(intervalType = "Work", intervalDuration = 10000, intervalName = "Push Up"),
     newInterval: (IntervalsInfo) -> Unit = {}

                           ){


    Card (modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.Red)

    ) {
        Text(modifier = Modifier.padding(top = 3.dp, start = 3.dp), text = "Default Interval :", fontSize = 20.sp, color = Color.White)

        IntervalModifier(modifier = Modifier.padding(horizontal = 3.dp, vertical = 3.dp) ,
            intervalToModify = interval,
            elevation = 35 ,
            showColumn = false,
            newInterval = {
   newInterval( IntervalsInfo( intervalType = it.intervalType, intervalDuration = it.intervalDuration,  intervalName = it.intervalName,   uri = it.uri ))
                          }

        )
    }

}
