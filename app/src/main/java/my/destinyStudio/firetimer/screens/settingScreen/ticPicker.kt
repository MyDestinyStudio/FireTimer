package my.destinyStudio.firetimer.screens.settingScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun  TicPicker(
    modifier: Modifier = Modifier ,
               currentTic:Long = 200L,
               newTicValue : ( Long)->Unit = {}  ){

    val ticValues = listOf(1000L, 500L, 250L, 200L, 100L, 50L, 25L, 20L, 10L, 5L, 2L)
    val timeLabels = ticValues.map {
        when (it) {
            1000L -> "1 s"
            500L -> "500 ms"
            250L -> "250 ms"
            200L -> "200 ms"
            100L -> "100 ms"
            50L -> "50 ms"
            25L -> "25 ms"
            20L -> "20 ms"
            10L -> "10 ms"
            5L -> "5 ms"
            else -> "2 ms"
        }
    }
    val selectedValue = remember { mutableLongStateOf(currentTic) } // Initial value (1000ms)
    val customValue = remember { mutableLongStateOf(0L) }
    val isCustomSelected = remember { mutableStateOf(false) }

    Card  (
        modifier =modifier

        , colors = CardDefaults.cardColors(containerColor = Color.Red)


    ) {
        Text(modifier = Modifier.padding(start = 12.dp), text = "Select Time :", style = MaterialTheme.typography.headlineMedium, color = Color.White)

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(modifier = Modifier.height(360.dp) , columns = GridCells.Fixed(2)) {
            itemsIndexed(ticValues){
                    index, value ->
                Row(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedValue.longValue == value,
                        onClick = { selectedValue.longValue = value
                            newTicValue(selectedValue.longValue)
                              isCustomSelected.value = false
                                  }
                        , colors = RadioButtonColors(selectedColor = Color.Blue,
                            unselectedColor = Color.White
                            , disabledSelectedColor = Color.Gray,
                            disabledUnselectedColor = Color.Gray)
                    )
                    Text(timeLabels[index], color = Color.White)
                }
            }
            item{
                Row(
                     modifier = Modifier.fillMaxWidth().height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isCustomSelected.value,
                        onClick = {
                            isCustomSelected.value = true
                            selectedValue.longValue = customValue.longValue
                                //  newTicValue(customValue.longValue)
                                  }
                        ,colors = RadioButtonColors(selectedColor = Color.Blue,
                            unselectedColor = Color.White
                            , disabledSelectedColor = Color.Gray,
                            disabledUnselectedColor = Color.Gray)
                    )

                    OutlinedTextField(
                        modifier = Modifier.padding(end = 10.dp).height(55.dp),
                        value = customValue.longValue.toString(),
                        onValueChange = { if(it.toLong() in 1..2999){customValue.longValue = it.toLong()
                            newTicValue(customValue.longValue)}
                            else{ customValue.longValue=1}
                                        },
                        label = { Text(" Value (ms)", color = Color.White) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors( focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor  = Color.White,
                            unfocusedBorderColor = Color.White,



                            ),


                        )
                }

            }
        }



        Spacer(modifier = Modifier.height(8.dp))

        Text(
           modifier = Modifier.align(Alignment.CenterHorizontally ),
            text = "the lowest the value the smoothest the ui",
            color = Color.White )
        Spacer(modifier = Modifier.height(8.dp))


    }



}