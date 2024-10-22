package my.destinyStudio.firetimer.screens.starscreen



import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import my.destinyStudio.firetimer.R
import my.destinyStudio.firetimer.components.Intervals
import my.destinyStudio.firetimer.components.RepeatNumber
import my.destinyStudio.firetimer.data.ExercisesViewModel
import my.destinyStudio.firetimer.navigation.Screens
import my.destinyStudio.firetimer.screens.savedworkouts.TopAppBarA
import my.destinyStudio.firetimer.screens.timerscreen.TimerBViewModel
import my.destinyStudio.firetimer.ui.theme.dimens
import my.destinyStudio.firetimer.utils.formatToMMSS
import my.destinyStudio.firetimer.utils.workOutListBuilder
import my.destinyStudio.firetimer.utils.workoutGenerator


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartScreen(
    navController: NavController=NavController(context =   LocalContext.current),
    exercisesViewModel :ExercisesViewModel= hiltViewModel(),
    timerBViewModel: TimerBViewModel = hiltViewModel(),

    ){

    val workout by exercisesViewModel.workout.collectAsState()
    val configuration = LocalConfiguration.current
    var warmUpDuration by rememberSaveable { mutableLongStateOf(10000) }
    var workOutDuration by rememberSaveable { mutableLongStateOf(10000 ) }
    var restDuration by rememberSaveable { mutableLongStateOf(10000 ) }
    var workRestCycles by rememberSaveable { mutableIntStateOf(2) }
    var restBtSetsDuration by rememberSaveable { mutableLongStateOf(12000) }
    var setsCycles by rememberSaveable  { mutableIntStateOf(1) }
    var coolDownDuration by rememberSaveable { mutableLongStateOf(12000) }






   LaunchedEffect(key1 = Unit) {
       exercisesViewModel.loadW(warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
           workRestC =  workRestCycles,
           restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration)
       Log.d("A"," Screen Dimes ${configuration.screenWidthDp  } +${configuration.screenHeightDp}")
   }


    Scaffold(modifier = Modifier.fillMaxSize(),

        topBar = {
            when {
                configuration.orientation==Configuration.ORIENTATION_LANDSCAPE &&configuration.screenHeightDp<480 -> {

                }
                else -> {
                    TopAppBarA (title =
                    " ${formatToMMSS(workout?.workOutPrimaryDetail?.sumOf{it.intervalDuration } ?: 0L)} |" +
                            stringResource(
                                R.string.intervals,
                                workout?.workOutPrimaryDetail?.size ?: 0
                            ) +
                            stringResource(R.string.sets, setsCycles),
                        actionButtons = emptyList() )
                }
            }
                 },
        floatingActionButton = {
DoubleDeckFab(

    onPlay = {

        timerBViewModel.retrieveW  (   warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
        workRestC =  workRestCycles,
        restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )


        navController.navigate(Screens.TimerBScreen.route)
             } ,


    onSave = {
    exercisesViewModel.addWorkOut(workoutGenerator(
        list = workOutListBuilder(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
        workRestC =  workRestCycles,
        restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )))
    }
)


        }
    ) {   paddingValues ->

        when (configuration.orientation) {



            Configuration.ORIENTATION_PORTRAIT -> {
                Column(modifier = Modifier
                    .padding(paddingValues)
                    .background(Color.Transparent) )
                {


                    Intervals(modifier = Modifier
                        .weight(1f)
                        .padding(3.dp) ,imageId = R.drawable.warmup, pDuration = warmUpDuration, intervalType = stringResource(
                        R.string.warm_up
                    )
                    ){ warmUpTime -> warmUpDuration=warmUpTime
                        exercisesViewModel.loadW( warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )
                    }




                    Intervals(modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),imageId = R.drawable.dumbbell, pDuration = workOutDuration , intervalType = stringResource(
                        R.string.work
                    )
                    ){ workOutTime -> workOutDuration=workOutTime
                        exercisesViewModel.loadW(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )
                    }



                    Intervals(modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),imageId = R.drawable.rest,pDuration = restDuration, intervalType = stringResource(
                        R.string.rest
                    )
                    ){ restTime -> restDuration=restTime
                        exercisesViewModel.loadW(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )
                    }




                    RepeatNumber(modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),imageId = R.drawable.workrest, label = stringResource(R.string.work_rest_number), initialC = workRestCycles){ wrCycles ->workRestCycles =wrCycles
                        exercisesViewModel.loadW(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )


                    }



                    Intervals(modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),imageId = R.drawable.resting, pDuration = restBtSetsDuration, intervalType = stringResource(
                        R.string.rest_btw_sets
                    )
                    ){ restBtSetsTime -> restBtSetsDuration=restBtSetsTime
                        exercisesViewModel.loadW(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration ) }



                    RepeatNumber(modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),imageId =R.drawable.repeat , label = stringResource(R.string.sets_number), initialC = setsCycles){ setNumber ->setsCycles =setNumber
                        exercisesViewModel.loadW(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )
                    }



                    Intervals(modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),imageId = R.drawable.cool_down, pDuration = coolDownDuration, intervalType = stringResource(  R.string.cool_down  )
                    ){ coolDownTime -> coolDownDuration=coolDownTime
                        exercisesViewModel.loadW(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )
                    }




                }

            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                Column(modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())  )
                {
                    Intervals(modifier = Modifier
                        .height(MaterialTheme.dimens.cardStarHeight)
                        .padding(3.dp) ,imageId = R.drawable.warmup, pDuration = warmUpDuration, intervalType = stringResource(  R.string.warm_up  )){ warmUpTime -> warmUpDuration=warmUpTime
                        exercisesViewModel.loadW( warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )
                    }




                    Intervals(modifier = Modifier
                        .height(MaterialTheme.dimens.cardStarHeight)
                        .padding(3.dp) ,imageId = R.drawable.dumbbell, pDuration = workOutDuration , intervalType = stringResource(  R.string.work  )){ workOutTime -> workOutDuration=workOutTime
                        exercisesViewModel.loadW(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )
                    }



                    Intervals(modifier = Modifier
                        .height(MaterialTheme.dimens.cardStarHeight)
                        .padding(3.dp),imageId = R.drawable.rest,pDuration = restDuration, intervalType = stringResource(  R.string.rest )){ restTime -> restDuration=restTime
                        exercisesViewModel.loadW(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )
                    }




                    RepeatNumber(modifier = Modifier
                        .height(MaterialTheme.dimens.cardStarHeight)
                        .padding(3.dp) ,imageId = R.drawable.workrest, label = stringResource(  R.string.work_rest_number ), initialC = workRestCycles){wrCycles ->workRestCycles =wrCycles
                        exercisesViewModel.loadW(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )


                    }



                    Intervals(modifier = Modifier
                        .height(MaterialTheme.dimens.cardStarHeight)
                        .padding(3.dp),imageId = R.drawable.resting, pDuration = restBtSetsDuration, intervalType = stringResource(  R.string.rest_btw_sets  )){ restBtSetsTime -> restBtSetsDuration=restBtSetsTime
                        exercisesViewModel.loadW(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration ) }



                    RepeatNumber(modifier = Modifier
                        .height(MaterialTheme.dimens.cardStarHeight)
                        .padding(3.dp),imageId =R.drawable.repeat , label = stringResource(  R.string.sets_number  ), initialC = setsCycles){setNumber ->setsCycles =setNumber
                        exercisesViewModel.loadW(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )
                    }



                    Intervals(modifier = Modifier
                        .height(MaterialTheme.dimens.cardStarHeight)
                        .padding(3.dp)  ,imageId = R.drawable.cool_down, pDuration = coolDownDuration, intervalType = stringResource(  R.string.cool_down  )){ coolDownTime -> coolDownDuration=coolDownTime
                        exercisesViewModel.loadW(  warmUpD = warmUpDuration, workOutD = workOutDuration, restD =  restDuration,
                            workRestC =  workRestCycles,
                            restBtSets = restBtSetsDuration, setsC = setsCycles, coolDownD =  coolDownDuration )
                    }




                }

            }


        }




    }
  }







