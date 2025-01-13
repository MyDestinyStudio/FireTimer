package my.destinyStudio.firetimer.screens.calendarScreen

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import my.destinyStudio.firetimer.navigation.ScheduleScreen
import my.destinyStudio.firetimer.navigation.StartScreen
import my.destinyStudio.firetimer.screens.settingScreen.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun  CalenderScreen(navController: NavController,

                    settingViewmodel: SettingsViewModel) {
    val datePickerState = rememberDatePickerState()

    BackHandler   {
        settingViewmodel.indexPage(2)
        navController.navigate(StartScreen, navOptions { popUpTo(ScheduleScreen){inclusive=true} } )

       Log.d("Screens","BackHandler Calendar ")
    }


    Scaffold(modifier = Modifier.fillMaxSize()) {

        innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {


            DatePicker(

                state = datePickerState,

                modifier = Modifier.padding(16.dp),

                colors = DatePickerDefaults.colors(
                    containerColor = Color.Cyan,
                    todayContentColor = Color.Red,
                    selectedDayContainerColor = Color.Magenta
                                                )

                )

        }

    }


}