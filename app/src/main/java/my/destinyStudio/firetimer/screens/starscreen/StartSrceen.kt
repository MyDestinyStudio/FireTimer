package my.destinyStudio.firetimer.screens.starscreen



import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import my.destinyStudio.firetimer.components.Intervals
import my.destinyStudio.firetimer.components.RepeatNumber
import my.destinyStudio.firetimer.data.ExercisesViewModel
import my.destinyStudio.firetimer.data.IntervalsInfo
import my.destinyStudio.firetimer.data.initial
import my.destinyStudio.firetimer.navigation.Screens
import my.destinyStudio.firetimer.screens.savedworkouts.TopAppBarA
import my.destinyStudio.firetimer.screens.timerscreen.TimerBViewModel
import my.destinyStudio.firetimer.ui.theme.AppColors
import my.destinyStudio.firetimer.utils.formatToMMSS
import my.destinyStudio.firetimer.utils.workOutListBuilder
import my.destinyStudio.firetimer.utils.workoutGenerator
import java.sql.Date
import java.time.Instant


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartScreen(
    navController: NavController=NavController(context =   LocalContext.current),
    exercisesViewModel :ExercisesViewModel= hiltViewModel(),
    timerBViewModel: TimerBViewModel = hiltViewModel(),

    ){


    val configuration = LocalConfiguration.current
    var warmUpDuration by rememberSaveable { mutableLongStateOf(10000) }
    var workOutDuration by rememberSaveable { mutableLongStateOf(3000 ) }
    var restDuration by rememberSaveable { mutableLongStateOf(3000 ) }
    var workRestCycles by rememberSaveable { mutableIntStateOf(2) }
    var restBtSetsDuration by rememberSaveable { mutableLongStateOf(20000) }
    var setsCycles by rememberSaveable  { mutableIntStateOf(1) }
    var coolDownDuration by rememberSaveable { mutableLongStateOf(10000) }







    val currentTimes by rememberSaveable  { mutableStateOf(Date.from(Instant.now()).toString())    }


    var newWorkout  by remember  { mutableStateOf(initial) }


    val listE   = remember {  mutableStateListOf<IntervalsInfo>(  )   }




    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {

    Scaffold(modifier = Modifier.fillMaxSize(),

        bottomBar = {
            BottomAppBarStar(
     listIcon  ={  navController.navigate(Screens.SavedWorkOutScreen.route)  },
            startIcon ={   newWorkout = workoutGenerator( name ="Work Out" , list =
    workOutListBuilder(   warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
        workRestC =  workRestCycles,
                    restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )
            )
                 timerBViewModel.retrieveW(   newWorkout)
                navController.navigate(Screens.TimerBScreen.route)
            },
            saveIcon ={
             newWorkout=workoutGenerator(name=currentTimes,list = listE)
             exercisesViewModel.addWorkOut(newWorkout)
            }
            )

  },
        topBar = {
            TopAppBarA (title =
            " ${formatToMMSS(listE.sumOf{it.intervalDuration })} |" +
            " ${listE .size} Intervals |" +
                    "sets $setsCycles"

                ,
                navigationIcon = {       },
                infoIcon = {   navController.navigate(Screens.InfoScreen.route)  },
                settingIcon = {navController.navigate(Screens.SettingScreen.route)}
                 )
        }
    ) {   paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .background(Color.Transparent) ) {

            Column(modifier = Modifier .weight(1f) ){
                Intervals(imageId = 1, pDuration = warmUpDuration, intervalType = "Warm Up"){ warmUpTime -> warmUpDuration=warmUpTime   }
            }


            Column(modifier = Modifier.weight(1f)) {
                Intervals(imageId = 1, pDuration = workOutDuration , intervalType = "Work"){ workOutTime -> workOutDuration=workOutTime }
            }

            Column(modifier = Modifier.weight(1f)) { Intervals(imageId = 1, pDuration = restDuration, intervalType = "Rest"){ restTime -> restDuration=restTime }

            }

            Column(modifier = Modifier.weight(0.8f)) {
                RepeatNumber("Work/Rest number", initialC = workRestCycles){wrCycles ->workRestCycles =wrCycles  }
            }

            Column(modifier = Modifier.weight(1f)) {
                Intervals(imageId = 1, pDuration = restBtSetsDuration, intervalType = "Rest Btw Sets"){ restBtSetsTime -> restBtSetsDuration=restBtSetsTime }
            }

            Column(modifier = Modifier .weight(0.8f)) {
                RepeatNumber(" Sets number ", initialC = setsCycles){setNumber ->setsCycles =setNumber  }
            }

            Column(modifier = Modifier.weight(1f)) {
                Intervals(imageId = 1, pDuration = coolDownDuration, intervalType = "Cool Down"){ coolDownTime -> coolDownDuration=coolDownTime }
            }
            listE.clear()
            listE .addAll(
                workOutListBuilder(
                    warmUpD = warmUpDuration,
                    workOutD = workOutDuration,
                    restD =  restDuration,
                    workRestC =  workRestCycles,
                    restBtSets = restBtSetsDuration,
                    setsC = setsCycles,
                    coolDownD =  coolDownDuration
                ))


                }


    }
        }
        Configuration.ORIENTATION_LANDSCAPE ->{
            Scaffold(modifier = Modifier.fillMaxSize(),
                containerColor = Color.White ,
                bottomBar = {
                    BottomAppBarStar(
                        listIcon  ={  navController.navigate(Screens.SavedWorkOutScreen.route)  },
                        startIcon ={   newWorkout = workoutGenerator( name ="Work Out" , list =
                        workOutListBuilder(   warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )
                        )
                            timerBViewModel.retrieveW  ( newWorkout)
                            navController.navigate(Screens.TimerBScreen.route)
                        },
                        saveIcon ={
                            newWorkout=workoutGenerator(name=currentTimes,list = listE)
                            exercisesViewModel.addWorkOut(newWorkout)
                        },
                        height = 40
                    )

                },
                topBar = {
                    TopAppBarA (title =
                    " ${formatToMMSS(listE.sumOf{it.intervalDuration })} |" +
                            " ${listE .size} Intervals |" +
                            "sets $setsCycles"

                        ,
                        navigationIcon = {       },
                        infoIcon = {   navController.navigate(Screens.InfoScreen.route)  },
                        settingIcon = {navController.navigate(Screens.SettingScreen.route)},
                   //     height = 40
                    )
                }
            ) {   paddingValues ->
                Column(modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())  ) {

     Column(modifier = Modifier.height(100.dp)) {
         Intervals(imageId = 1,intervalType = "Warm Up"){ warmUpTime -> warmUpDuration=warmUpTime   }
     }


  Column(modifier = Modifier.height(100.dp)) {
            Intervals(imageId = 1, pDuration = workOutDuration , intervalType = "Work"){ workOutTime -> workOutDuration=workOutTime }
  }

    Column(modifier = Modifier.height(100.dp)) { Intervals(imageId = 1, pDuration = restDuration, intervalType = "Rest"){ restTime -> restDuration=restTime }

    }

   Column(modifier = Modifier.height(100.dp)) {
                        RepeatNumber("Work/Rest number", initialC = workRestCycles){wrCycles ->workRestCycles =wrCycles  }
                    }

     Column(modifier = Modifier.height(100.dp)) {
                        Intervals(imageId = 1, pDuration = restBtSetsDuration, intervalType = "Rest Btw Sets"){ restBtSetsTime -> restBtSetsDuration=restBtSetsTime }
                    }

     Column(modifier = Modifier .height(100.dp)) {
                        RepeatNumber(" Sets number ", initialC = setsCycles){setNumber ->setsCycles =setNumber  }
                    }
   Column(modifier = Modifier.height(100.dp)) {
           Intervals(imageId = 1, pDuration = coolDownDuration, intervalType = "Cool Down"){ coolDownTime -> coolDownDuration=coolDownTime }
       }



                    listE.clear()
                    listE .addAll(
                        workOutListBuilder(
                            warmUpD = warmUpDuration,
                            workOutD = workOutDuration,
                            restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration,
                            setsC = setsCycles,
                            coolDownD =  coolDownDuration
                        ))


                }


            }



        }




    }

}



@Preview
@Composable
fun BottomAppBarStar(
   listIcon:() -> Unit ={},
    startIcon:() -> Unit ={},
    saveIcon:() -> Unit ={},
   height:Int = 70
){
   Surface(
       modifier = Modifier
           .fillMaxWidth()
           .height(height.dp),
        color = AppColors.mBlueL

   ) {
        Row(Modifier.fillMaxWidth() ,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(modifier = Modifier .weight(1f),
                onClick = {
                    listIcon()
                }

            ) {
                Icon(imageVector = Icons.Filled.FormatListNumbered,
                    contentDescription = "settings", modifier = Modifier.fillMaxSize(), tint = Color.White
                )
            }

            IconButton(modifier = Modifier .weight(1f),
                onClick = {
                    startIcon()
                }

            ) {
                Icon(imageVector = Icons.Filled.PlayArrow ,
                    contentDescription = "start", modifier = Modifier.fillMaxSize(),
                    tint = Color.White
                )
            }


            IconButton(modifier = Modifier .weight(1f),
                onClick = {
                    saveIcon()

                }

            ) {
                Icon(imageVector = Icons.Filled.Save,
                    contentDescription = "settings", modifier = Modifier.fillMaxSize()
                    , tint = Color.White

                )
            }



        }
    }

}
