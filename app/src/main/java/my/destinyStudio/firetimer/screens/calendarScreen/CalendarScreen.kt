package my.destinyStudio.firetimer.screens.calendarScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun  CalenderScreen(){
    val datePickerState = rememberDatePickerState()
    Scaffold(modifier = Modifier.fillMaxSize()) {

        innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {


            DatePicker(state = datePickerState, modifier = Modifier.padding(16.dp),
colors = DatePickerDefaults.colors(containerColor = Color.Cyan,
    todayContentColor = Color.Red,
    selectedDayContainerColor = Color.Magenta


)

                )

        }

    }


}