package my.destinyStudio.firetimer.screens.editworkout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import my.destinyStudio.firetimer.data.ExpTAI
import my.destinyStudio.firetimer.data.IntervalsInfoIndexed
import my.destinyStudio.firetimer.ui.theme.AppColors
import my.destinyStudio.firetimer.ui.theme.IntervalTypeColors
import my.destinyStudio.firetimer.utils.formatToMMSS


@Composable
//@Preview
fun NameChangeAlertDialog(initialName: String?="", onDismiss: () -> Unit={}, onConfirm: (String?) -> Unit={} ) {

    var newName by remember {
        mutableStateOf(initialName)
    }
    AlertDialog(onDismissRequest = { onDismiss()},
        confirmButton = {   Button(onClick = { onConfirm(newName) }){   Text(text = "Update") }     }
        , dismissButton = {   Button(onClick = { onDismiss() }){  Text(text = "Dismiss") }     },
        title = { Text(text = "Update Name ") },
        text = { TextField(value = newName.toString(), onValueChange = {newName=it }) }

    )

}


@Composable
@Preview
fun TopAppBarEditWorkOuts(nameW: String?="name", duration: Int=8000, intervalsNumber: Int=4,
                          setsNumber:Int =4, onReviewClick: () -> Unit={},
                          textClicked: () -> Unit={}, onBackClick: () -> Unit={},

            onReset : () -> Unit={}) {

    Surface (modifier = Modifier
        .fillMaxWidth()
        .height(80.dp),
        color = AppColors.mBlueL){

        Column {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(modifier = Modifier,
                    onClick = {
                        onBackClick()
                    }

                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "settings",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.White
                    )
                }


                Row(modifier = Modifier
                    .weight(1f)
                    .clickable { textClicked() }, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = nameW.toString(),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,

                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                }

                IconButton(modifier = Modifier,
                    onClick = {
                        onReviewClick()

                    }

                ) {
                    Icon(
                        imageVector = Icons.Filled.RemoveRedEye,
                        contentDescription = "start", modifier = Modifier.fillMaxSize(),
                        tint = Color.White
                    )
                }
                IconButton(modifier = Modifier,
                    onClick = {

                        onReset()
                    }

                ) {
                    Icon(
                        imageVector = Icons.Filled.Restore,
                        contentDescription = "start", modifier = Modifier.fillMaxSize(),
                        tint = Color.White
                    )
                }
            }
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(text = " ${formatToMMSS(duration.toLong()) } | $intervalsNumber Intervals | $setsNumber Sets ",
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,

                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold)
            }
        }

    }

}

@Composable
//@Preview
fun ExercisesReview(listOfIndexedInterval:MutableList<IntervalsInfoIndexed> = ExpTAI, onDismiss: () -> Unit={}, cardClicked: (Int) -> Unit={}) {

    Dialog(onDismissRequest = { onDismiss() }) {
        Card {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(3.dp),
                contentPadding = PaddingValues(horizontal = 2.dp, vertical = 7.dp)
            ) {
                itemsIndexed(listOfIndexedInterval) { index, interval ->
                    Card(modifier = Modifier .clickable { cardClicked(index) },
                        shape = RoundedCornerShape(topStart = 0.dp, bottomStart = 30.dp, topEnd = 30.dp, bottomEnd = 0.dp)) {
                        val backColor = when(interval.intervalType){
                            IntervalsType.WARM_UP -> IntervalTypeColors.warmUpColor
                            IntervalsType.WORK_OUT-> IntervalTypeColors.workoutColor
                            IntervalsType.REST -> IntervalTypeColors.restColor
                            IntervalsType.ACTIVE_REST -> IntervalTypeColors.activeColor
                            IntervalsType.REST_BTW_SETS-> IntervalTypeColors.restBteSetsUpColor
                            IntervalsType.COOL_DOWN-> IntervalTypeColors.coolDownColor
                            else  -> IntervalTypeColors.otherColor


                        }
                        Row(
                            modifier = Modifier
                                .background(Brush.linearGradient(backColor))
                                .fillMaxWidth()
                                .height(30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(modifier = Modifier.weight(1f), text = "${index+1} :", color = Color.White)
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

