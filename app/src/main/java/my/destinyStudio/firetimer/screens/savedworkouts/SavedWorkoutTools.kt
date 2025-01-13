package my.destinyStudio.firetimer.screens.savedworkouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import my.destinyStudio.firetimer.R
import my.destinyStudio.firetimer.components.IntervalsType
import my.destinyStudio.firetimer.data.IntervalsInfo
import my.destinyStudio.firetimer.data.WorkOutDetail
import my.destinyStudio.firetimer.data.wdTest
import my.destinyStudio.firetimer.ui.theme.AppColors
import my.destinyStudio.firetimer.ui.theme.IntervalTypeColors
import my.destinyStudio.firetimer.ui.theme.dimens
import my.destinyStudio.firetimer.utils.formatToMMSS

@Composable
 //@Preview
fun ExercisesReviewS(workoutToReview: WorkOutDetail = wdTest, onDismiss: () -> Unit={}  ) {

    val duration = formatToMMSS(workoutToReview.workOutPrimaryDetail.sumOf {it.intervalDuration.toInt()}.toLong())
    val sets =  workoutToReview.workOutPrimaryDetail.count { it.intervalType== IntervalsType.REST_BTW_SETS+1}
    val size = workoutToReview.workOutPrimaryDetail.size

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(Modifier.padding(vertical = 20.dp, horizontal =30.dp),
           ) {

                Column(modifier = Modifier.fillMaxWidth() , horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = workoutToReview.workOutName, textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis, fontSize = 27.sp)
                    Text(text ="$duration | $sets sets | $size Intervals"  )
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    contentPadding = PaddingValues(horizontal = 2.dp, vertical = 7.dp)
                ) {
                    itemsIndexed(workoutToReview.workOutPrimaryDetail) { index, interval ->
                        Card(modifier = Modifier, shape = RoundedCornerShape(topStart = 0.dp, bottomStart = 30.dp, topEnd = 30.dp, bottomEnd = 0.dp) ) {
                            val backColor = when (interval.intervalType) {
                                IntervalsType.WARM_UP -> IntervalTypeColors.warmUpColor
                                IntervalsType.WORK_OUT -> IntervalTypeColors.workoutColor
                                IntervalsType.REST -> IntervalTypeColors.restColor
                                IntervalsType.ACTIVE_REST -> IntervalTypeColors.activeColor
                                IntervalsType.REST_BTW_SETS -> IntervalTypeColors.restBteSetsUpColor
                                IntervalsType.COOL_DOWN -> IntervalTypeColors.coolDownColor
                                else -> IntervalTypeColors.otherColor


                            }
                            Row(
                                modifier = Modifier
                                    .background(Brush.linearGradient(backColor))
                                    .fillMaxWidth()
                                    .height(40.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(modifier = Modifier.weight(1f), text = "${index + 1} :", color = Color.White)
                                Text(
                                    modifier = Modifier.weight(2f),
                                    text = "${interval.intervalType} : ${interval.intervalName}",
                                    overflow = TextOverflow.Ellipsis, color = Color.White
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = formatToMMSS(interval.intervalDuration), color = Color.White
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                            }

                        }

                    }

                }
            }


    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview
fun  TopAppBarA (title: String="",
  actionButtons:List<Pair<ImageVector,String>> =
                     listOf(
                         Pair(Icons.Rounded.Add,"onAdd"),
                        Pair(Icons.Rounded.Remove,"onRemove")

                        )
 ) {

    TopAppBar(modifier = Modifier.height(MaterialTheme.dimens.appTopBar),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppColors.verdigris ,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(  R.string.app_name  ) + if (title != "") {
                        " : "
                    } else {
                        ""
                    },
                    fontSize = MaterialTheme.dimens.appTitle.sp,
                  fontWeight = FontWeight.ExtraBold,
                    color = AppColors.WHITE
                )
                if (title != ""){
                    Text(
                        text = title,
                        fontSize = MaterialTheme.dimens.appTitleSuffix.sp,
                        color = AppColors.WHITE
                    )
                }
            }

        },
        actions = {
            actionButtons.forEach {IconButton(modifier = Modifier.fillMaxHeight(),onClick = {it.second}) {
                Icon( imageVector = it.first, contentDescription = "", tint =AppColors.WHITE)

            }  }

        }
    )


    }


@Composable
//@Preview
fun CardInfo( modifier :Modifier = Modifier,
    title : String="BolBol",
    subString : String="12:14"

){
    Card(
        modifier = modifier

        , colors = CardDefaults.cardColors(containerColor = Color.Red),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(3.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box( modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(  textAlign = TextAlign.Center,
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }


            Box( modifier = Modifier.weight(1f),  contentAlignment = Alignment.Center) {
                Text(  text = subString, style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

        }

    }

}



//@Preview
@Composable
fun  IntervalDots(index:Int =1, interval: IntervalsInfo
= IntervalsInfo("Work", 10000,"Push Up")
) {
    Column {
        Text(text = "${index+1 }. ${interval.intervalType} : ${interval.intervalName} ",
            overflow = TextOverflow.Ellipsis, color = Color.White   )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.5.dp,
            color = Color.White
        )

    }


}


@Composable
//@Preview
fun DeleteWorkoutAlertDialog(onDismiss: () -> Unit={}, onConfirm: () -> Unit={}) {

    AlertDialog(text = { Text(text = stringResource(R.string.delete))}, title = { Text(text = stringResource( R.string.are_you_sure
    )
    )},
        onDismissRequest = { onDismiss() },
        confirmButton = {
            IconButton(onClick = { onConfirm() }, content = {
                Icon(  imageVector = Icons.Filled.Check, contentDescription = "", tint = Color.Green )  })

        },
        dismissButton = {
            IconButton(onClick = { onDismiss() }, content = { Icon(imageVector = Icons.Filled.RadioButtonUnchecked, contentDescription = "", tint = Color.Red)})

                       }

    )

}

