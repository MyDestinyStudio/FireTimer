package my.destinyStudio.firetimer.screens.savedworkouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import my.destinyStudio.firetimer.components.IntervalsType
import my.destinyStudio.firetimer.data.IntervalsInfo
import my.destinyStudio.firetimer.data.WorkOutDetail
import my.destinyStudio.firetimer.data.wdTest
import my.destinyStudio.firetimer.ui.theme.AppColors
import my.destinyStudio.firetimer.ui.theme.IntervalTypeColors
import my.destinyStudio.firetimer.utils.formatToMMSS

@Composable
//@Preview
fun ExercisesReviewS(workoutToReview: WorkOutDetail = wdTest, onDismiss: () -> Unit={}  ) {

    val duration = formatToMMSS(workoutToReview.workOutPrimaryDetail.sumOf {it.intervalDuration.toInt()}.toLong())
    val sets =  workoutToReview.workOutPrimaryDetail.count { it.intervalType== IntervalsType.REST_BTW_SETS+1}
    val size = workoutToReview.workOutPrimaryDetail.size

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(Modifier.padding(vertical = 60.dp, horizontal =30.dp),
           ) {
            Column {
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

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview
fun  TopAppBarA (title: String="BamBamBamBamBamBamBamBamBamBamBamBam",
                 navigationIcon: () -> Unit ={},
                 infoIcon: () -> Unit={},
                 settingIcon: () -> Unit={},
                 //height:Int =70


) {

        CenterAlignedTopAppBar(


            navigationIcon = {
                IconButton(modifier = Modifier, onClick = { navigationIcon() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = " ",
                        tint = Color.White
                    )
                }
            },
            title = {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    color = Color.White
                )
            },
            actions = {
                IconButton(modifier = Modifier, onClick = { infoIcon() }) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = " ",
                        tint = Color.White
                    )


                }
                IconButton(modifier = Modifier, onClick = { settingIcon() }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = " ",
                        tint = Color.White
                    )

                }


            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = AppColors.mBlueL),
           // scrollBehavior =TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
            //TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
            //TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        // TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        )
    }


@Composable
//@Preview
fun CardInfo(
    title : String="BolBol",
    subString : String="12:14"

){
    Card(
        modifier = Modifier
//            .height(50.dp)
//            .width(70.dp)
        , colors = CardDefaults.cardColors(containerColor = Color.Red),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
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
        Divider(
            color = Color.White,
            thickness = 1.5.dp,
            modifier = Modifier.fillMaxWidth()
        )

    }


}

@Composable
 @Preview
fun InstructionsCard() {
    Surface(
        Modifier
            .fillMaxWidth()
            .height(70.dp),
        color = AppColors.mBlueL
    ){
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Text(text = "Tap", color = Color.White)
                Icon(imageVector = Icons.Filled.Edit, contentDescription ="", tint = Color.White )
                Text(text = "To Modify", color = Color.White)
            }
            Row {
                Text(text = "Tap ", color = Color.White)
                Icon(imageVector = Icons.Filled.Delete, contentDescription ="" , tint = Color.White)
                Text(text = "To Delete ", color = Color.White)
            }
            Row {
                Text(text = "Tap ", color = Color.White)
                Icon(imageVector = Icons.Filled.RemoveRedEye, contentDescription ="" , tint = Color.White)
                Text(text = "To Review ", color = Color.White)
            }


        }
    }
}

@Composable
//@Preview
fun DeleteWorkoutAlertDialog(onDismiss: () -> Unit={}, onConfirm: () -> Unit={}) {

    AlertDialog(text = { Text(text = "Delete ")}, title = { Text(text = " Are You Sure ? ")},
        onDismissRequest = { onDismiss() }, confirmButton = {
            IconButton(onClick = { onConfirm() }, content = { Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "", tint = Color.Green
            )})

        },
        dismissButton = {
            IconButton(onClick = { onDismiss() }, content = { Icon(
                imageVector = Icons.Filled.RadioButtonUnchecked,
                contentDescription = "", tint = Color.Red
            )})

        }

    )

}

