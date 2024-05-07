package my.destinyStudio.firetimer.screens.editworkout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.destinyStudio.firetimer.components.IntervalsType
import my.destinyStudio.firetimer.components.TimePickerCard
import my.destinyStudio.firetimer.data.IntervalsInfoIndexed
import my.destinyStudio.firetimer.ui.theme.IntervalTypeColors

//@Composable
// //@Preview
//fun IntervalModifierB(intervalToModify: IntervalsInfoIndexed =
//                         IntervalsInfoIndexed("Work", 10000,"Push Up"),
//                     moveUp: () -> Unit={},
//                     moveDown: () -> Unit={},
//                     deleteInterval: () -> Unit={},
//                     addInterval: () -> Unit={},
//                     newInterval: (IntervalsInfoIndexed) -> Unit={}) {
//    var newName by remember {
//        mutableStateOf(intervalToModify.intervalName)
//    }
//    var newDuration by remember {
//        mutableLongStateOf(intervalToModify.intervalDuration)
//    }
//    var newType by remember {
//        mutableStateOf(intervalToModify.intervalType)
//    }
//    val iD by remember {
//        mutableStateOf(intervalToModify.id)
//    }
//    newInterval(IntervalsInfoIndexed(intervalType = newType, intervalName = newName, intervalDuration = newDuration, id = iD))
//
//   val backColor = when(newType ){
//        IntervalsType.WARM_UP -> IntervalTypeColors.warmUpColor
//        IntervalsType.WORK_OUT-> IntervalTypeColors.workoutColor
//        IntervalsType.REST -> IntervalTypeColors.restColor
//        IntervalsType.ACTIVE_REST -> IntervalTypeColors.activeColor
//        IntervalsType.REST_BTW_SETS-> IntervalTypeColors.restBteSetsUpColor
//        IntervalsType.COOL_DOWN-> IntervalTypeColors.coolDownColor
//        else  -> IntervalTypeColors.otherColor
//
//
//    }
//
//    Card(modifier = Modifier
//        .fillMaxWidth()
//        .height(150.dp)
//        , colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
//        ConstraintLayout(modifier = Modifier
//            .fillMaxSize()
//            .background(Brush.linearGradient(backColor))) {
//            val(typeDrop,text,column, picker )= createRefs()
//
//
//            Box(modifier = Modifier.constrainAs(typeDrop){
//                start.linkTo(parent.start)
//                top.linkTo(parent.top )
//                end.linkTo(text.start)
//                bottom.linkTo(picker.top)
//
//            }, contentAlignment = Alignment.Center){
//                TypeOptionBox(initialType = newType) {
//                    newType= it
//                }
//            }
//
//
//            OutlinedTextField(
//                modifier = Modifier.constrainAs(text) {
//                    start.linkTo(typeDrop.end)
//                    top.linkTo(parent.top, margin = 5.dp)
//                    end.linkTo(column.start)
//
//                },
//                value = newName,
//                onValueChange = {
//                    newName = it
//
//                },
//                maxLines = 1,
//                placeholder = { Text(text = "Enter Name" , textAlign = TextAlign.Center) },
//                textStyle = TextStyle(textAlign = TextAlign.Center),
//
//                colors = OutlinedTextFieldDefaults.colors(
//                    unfocusedContainerColor = Color.Transparent,
//                    focusedContainerColor = Color.Transparent,
//                    focusedTextColor = Color.White,
//                    focusedPlaceholderColor = Color.Red,
//                    unfocusedTextColor = Color.White,
//                    disabledBorderColor = Color.White,
//                    focusedBorderColor = Color.White,
//                    unfocusedBorderColor = Color.White
//
//                ),
//                shape = RoundedCornerShape(12.dp),
//
//                )
//            Box(modifier = Modifier.constrainAs(picker){
//                start.linkTo(parent.start)
//                top.linkTo(text.bottom, margin = 3.dp  )
//                end.linkTo(parent.end)
//
//            }) {
//                TimePickerCard(secondsInPut = (intervalToModify.intervalDuration.toInt() /1000)%60,
//                    minutesInPut = (intervalToModify.intervalDuration.toInt() /1000)/60,
//                    borderColor = Color.White,
//                    color = Color.White
//
//                ) {
//                    newDuration=it
//
//                }
//            }
//
//
//            Column(modifier = Modifier
//               // .fillMaxHeight()
//                .constrainAs(column) {
//                    end.linkTo(parent.end)
//                    top.linkTo(parent.top)
//                    bottom.linkTo(parent.bottom)
//
//
//                }) {
//                IconButton(modifier = Modifier.weight(1f),onClick = { moveUp() }) {
//                    Icon(modifier = Modifier.fillMaxSize(), imageVector = Icons.Default.ArrowUpward, contentDescription = "Up", tint = Color.White)
//
//                }
//                IconButton(modifier = Modifier.weight(1f),onClick = { addInterval() }) {
//                    Icon(modifier = Modifier.fillMaxSize(),imageVector = Icons.Filled.Add, contentDescription = "Up", tint = Color.White)
//
//                }
//                IconButton(modifier = Modifier.weight(1f),onClick = { deleteInterval() }) {
//                    Icon(modifier = Modifier.fillMaxSize(),imageVector = Icons.Filled.Delete, contentDescription = "Up", tint = Color.White)
//
//                }
//                IconButton(modifier = Modifier.weight(1f),onClick = { moveDown() }) {
//                    Icon(modifier = Modifier.fillMaxSize(),imageVector = Icons.Default.ArrowDownward, contentDescription = "Up", tint = Color.White)
//
//                }
//
//            }
//
//
//        }
//
//
//    }
//
//
//
//
//}
@Composable
@Preview(showBackground = true , backgroundColor = 0xFFEFB8C8 )
fun TypeOptionBox(
                  initialType:String =IntervalsType.WORK_OUT,
                  sendType:(String)->Unit={}


) {
    var expanded by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf( initialType) }

    sendType(selectedType)

    Box(modifier = Modifier
         .width(120.dp)
        .clickable { expanded = true }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1f),
                text = initialType,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp
            )
            Icon(modifier = Modifier.weight(0.5f ),imageVector =if (expanded)Icons.Filled.ArrowDropUp
                              else Icons.Filled.ArrowDropDown
                , contentDescription = "", tint = Color.White)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(onClick = {
                selectedType = IntervalsType.WARM_UP
                expanded= false
            },
                text = {Text(text = IntervalsType.WARM_UP )}
            )
            DropdownMenuItem(onClick = {
                selectedType =IntervalsType.WORK_OUT
                expanded= false
            },
                text = {Text(text = IntervalsType.WORK_OUT )}
            )
            DropdownMenuItem(onClick = {
                selectedType =IntervalsType.REST
                expanded= false
            },
                text = {Text(text = IntervalsType.REST)}
            )

            DropdownMenuItem(onClick = {
                selectedType =IntervalsType.REST_BTW_SETS
                expanded= false
            },
                text = {Text(text = IntervalsType.REST_BTW_SETS)}
            )
            DropdownMenuItem(onClick = {
                selectedType =IntervalsType.ACTIVE_REST
                expanded= false
            },
                text = {Text(text = IntervalsType.ACTIVE_REST )}
            )
            DropdownMenuItem(onClick = {
                selectedType =IntervalsType.OTHER
                expanded= false
            },
                text = {Text(text = IntervalsType.OTHER )}
            )
        }
    }
}
@Composable
@Preview
fun IntervalModifier (intervalToModify: IntervalsInfoIndexed =
                         IntervalsInfoIndexed("Work", 10000,"Push Up"),
                     moveUp: () -> Unit={},
                     moveDown: () -> Unit={},
                     deleteInterval: () -> Unit={},
                     addInterval: () -> Unit={},
                     newInterval: (IntervalsInfoIndexed) -> Unit={}) {
    var newName by remember {
        mutableStateOf(intervalToModify.intervalName)
    }
    var newDuration by remember {
        mutableLongStateOf(intervalToModify.intervalDuration)
    }
    var newType by remember {
        mutableStateOf(intervalToModify.intervalType)
    }
    val iD by remember {
        mutableStateOf(intervalToModify.id)
    }

    newInterval(IntervalsInfoIndexed(intervalType = newType, intervalName = newName, intervalDuration = newDuration, id = iD))

    val backColor = when(newType ){
        IntervalsType.WARM_UP -> IntervalTypeColors.warmUpColor
        IntervalsType.WORK_OUT-> IntervalTypeColors.workoutColor
        IntervalsType.REST -> IntervalTypeColors.restColor
        IntervalsType.ACTIVE_REST -> IntervalTypeColors.activeColor
        IntervalsType.REST_BTW_SETS-> IntervalTypeColors.restBteSetsUpColor
        IntervalsType.COOL_DOWN-> IntervalTypeColors.coolDownColor
        else  -> IntervalTypeColors.otherColor


    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        , colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Row(modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(backColor)),
            verticalAlignment = Alignment.CenterVertically

        ) {



     Column (modifier = Modifier.weight(1f)  ,
         horizontalAlignment = Alignment.CenterHorizontally

     
     ){
                Row(modifier = Modifier.padding(bottom = 3.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.padding(2.dp), contentAlignment = Alignment.Center) {
                        TypeOptionBox(initialType = newType) {
                            newType = it
                        }
                    }


                        OutlinedTextField(
                            modifier = Modifier,
                            value = newName,
                            onValueChange = {
                                newName = it

                            },

                            maxLines = 1,
                            placeholder = {
                                Text(
                                    text = "Enter Name",
                                    textAlign = TextAlign.Center
                                )
                            },
                            textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 20.sp),

                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedTextColor = Color.White,
                                unfocusedPlaceholderColor = Color.White,
                                focusedPlaceholderColor = Color.White,
                                unfocusedTextColor = Color.White,
                                disabledBorderColor = Color.White,
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.White

                            ),
                            shape = RoundedCornerShape(12.dp),

                            )
                    }


                    TimePickerCard(
                        secondsInPut = (intervalToModify.intervalDuration.toInt() / 1000) % 60,
                        minutesInPut = (intervalToModify.intervalDuration.toInt() / 1000) / 60,
                        borderColor = Color.White,
                        color = Color.White

                    ) {
                        newDuration = it

                    }

                }


      Column(modifier = Modifier  , horizontalAlignment = Alignment.CenterHorizontally  ) {
                        IconButton(modifier = Modifier.weight(1f), onClick = { moveUp() }) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Default.ArrowUpward,
                                contentDescription = "Up",
                                tint = Color.White
                            )

                        }
                        IconButton(modifier = Modifier.weight(1f), onClick = { addInterval() }) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Up",
                                tint = Color.White
                            )

                        }
                        IconButton(modifier = Modifier.weight(1f), onClick = { deleteInterval() }) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Up",
                                tint = Color.White
                            )

                        }
                        IconButton(modifier = Modifier.weight(1f), onClick = { moveDown() }) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Default.ArrowDownward,
                                contentDescription = "Up",
                                tint = Color.White
                            )

                        }

                    }






    }


    }
}