package my.destinyStudio.firetimer.screens.starscreen



import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import my.destinyStudio.firetimer.R
import my.destinyStudio.firetimer.components.Intervals
import my.destinyStudio.firetimer.components.RepeatNumber
import my.destinyStudio.firetimer.data.ExercisesViewModel
import my.destinyStudio.firetimer.navigation.TimerBScreen
import my.destinyStudio.firetimer.screens.savedworkouts.TopAppBarA
import my.destinyStudio.firetimer.screens.settingScreen.SettingsViewModel
import my.destinyStudio.firetimer.screens.timerscreen.TimerBViewModel
import my.destinyStudio.firetimer.ui.theme.AppColors
import my.destinyStudio.firetimer.ui.theme.dimens
import my.destinyStudio.firetimer.utils.formatToMMSS
import my.destinyStudio.firetimer.utils.workOutListBuilder
import my.destinyStudio.firetimer.utils.workoutGenerator


@SuppressLint( "SwitchIntDef", "AutoboxingStateValueProperty")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartScreen(
    navController: NavController=NavController(context =   LocalContext.current),
    exercisesViewModel :ExercisesViewModel= hiltViewModel(),
    timerBViewModel: TimerBViewModel = hiltViewModel(),
    settingViewModel: SettingsViewModel = hiltViewModel(),

    ){

    val settingsLoaded by settingViewModel.settingsLoaded.collectAsState( )
    val settings       by settingViewModel.settings.collectAsState( )
    val workout        by exercisesViewModel.workout.collectAsState()



    var warmUpDuration     by rememberSaveable { mutableLongStateOf(settings.defaultWarmUpDuration) }
    var workOutDuration    by rememberSaveable  { mutableLongStateOf(settings.defaultWorkDuration) }
    var restDuration       by rememberSaveable  { mutableLongStateOf(settings.defaultRestDuration) }
    var workRestCycles     by rememberSaveable { mutableIntStateOf(settings.defaultCycles.toInt()) }
    var restBtSetsDuration by rememberSaveable  { mutableLongStateOf(settings.defaultRestBtwSetsDuration) }
    var setsCycles         by rememberSaveable { mutableIntStateOf(settings.defaultSets.toInt()) }
    var coolDownDuration   by rememberSaveable  { mutableLongStateOf(settings.defaultCoolDownDuration) }

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    
    val iconSize by remember { mutableIntStateOf(40) }


    BackHandler{
        (context as? ComponentActivity)?.moveTaskToBack(true)


        Log.d("Screens","BackHandler Start")
    }

    Scaffold(modifier = Modifier.fillMaxSize(),

            topBar = {
                when {
                    configuration.screenHeightDp < 480 -> {}
                    else -> {
  TopAppBarA(title = " ${formatToMMSS(workout?.workOutPrimaryDetail?.sumOf { it.intervalDuration } ?: 0L)} |" +
                    "${workout?.workOutPrimaryDetail?.size}" + stringResource(R.string.intervals) +
                                "$setsCycles" + stringResource(R.string.sets),
                            actionButtons = emptyList())
                    }
                }
            },
            floatingActionButton = {
                DoubleDeckFab(

                    onPlay = {
                        timerBViewModel.retrieveW(
                            warmUpD = warmUpDuration ,
                            workOutD = workOutDuration,
                            restD = restDuration,
                            workRestC = workRestCycles,
                            restBtSets = restBtSetsDuration,
                            setsC = setsCycles,
                            coolDownD = coolDownDuration
                        )

                        navController.navigate( TimerBScreen  )
                    },

                    onSave = {
                        exercisesViewModel.addWorkOut(
                            workoutGenerator(
                                list = workOutListBuilder(
                                    warmUpD = warmUpDuration ,
                                    workOutD = workOutDuration,
                                    restD = restDuration,
                                    workRestC = workRestCycles,
                                    restBtSets = restBtSetsDuration,
                                    setsC = setsCycles,
                                    coolDownD = coolDownDuration
                                )
                            )

                        )
                    }
                )


            }
        ) { paddingValues ->
            if (!settingsLoaded) {
                // Show a loading indicator until the settings are loaded
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
            when (configuration.orientation) {


                Configuration.ORIENTATION_PORTRAIT -> {

                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .background(Color.Transparent)
                    ) {


                        Intervals(
                            modifier = Modifier
                                .height(120.dp)
                                .padding(5.dp),
                            imageId = R.drawable.warmup,
                            pDuration = warmUpDuration ,
                            intervalType = stringResource(R.string.warm_up)
                        ) {

                                warmUpTime ->  warmUpDuration = warmUpTime

                            exercisesViewModel.loadW(
                                warmUpD = warmUpDuration ,
                                workOutD = workOutDuration,
                                restD = restDuration,
                                workRestC = workRestCycles,
                                restBtSets = restBtSetsDuration,
                                setsC = setsCycles,
                                coolDownD = coolDownDuration
                            )
                        }

                        Box {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 3.dp)
                                        .border(
                                            BorderStroke(3.dp, color =AppColors.verdigris),
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                ) {


                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(modifier = Modifier.padding(7.dp)) {

                                            Column {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .border(
                                                            BorderStroke(
                                                                3.dp,
                                                                color = AppColors.SafetyOrange
                                                            ), shape = RoundedCornerShape(20.dp)
                                                        )
                                                ) {

                                                    Column(
                                                        Modifier
                                                            .padding(bottom = (iconSize / 2 + 5).dp)
                                                            .fillMaxWidth(),
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {


                                                        Intervals(
                                                            modifier = Modifier
                                                                .height(120.dp)
                                                                .padding(5.dp),
                                                            imageId = R.drawable.dumbbell,
                                                            pDuration = workOutDuration,
                                                            intervalType = stringResource(R.string.work)
                                                        ) { workOutTime ->
                                                            workOutDuration = workOutTime
                                                            exercisesViewModel.loadW(
                                                                warmUpD = warmUpDuration ,
                                                                workOutD = workOutDuration,
                                                                restD = restDuration,
                                                                workRestC = workRestCycles,
                                                                restBtSets = restBtSetsDuration,
                                                                setsC = setsCycles,
                                                                coolDownD = coolDownDuration
                                                            )
                                                        }
                                                        Intervals(
                                                            modifier = Modifier
                                                                .height(120.dp)
                                                                .padding(
                                                                    top = 7.dp,
                                                                    start = 5.dp,
                                                                    end = 5.dp,
                                                                    bottom = 5.dp
                                                                ),
                                                            imageId = R.drawable.rest,
                                                            pDuration = restDuration,
                                                            intervalType = stringResource(R.string.rest)
                                                        ) { restTime ->
                                                            restDuration = restTime
                                                            exercisesViewModel.loadW(
                                                                warmUpD = warmUpDuration ,
                                                                workOutD = workOutDuration,
                                                                restD = restDuration,
                                                                workRestC = workRestCycles,
                                                                restBtSets = restBtSetsDuration,
                                                                setsC = setsCycles,
                                                                coolDownD = coolDownDuration
                                                            )
                                                        }


                                                    }
                                                }
                                                Spacer(
                                                    modifier = Modifier
                                                        .background(Color.Green)
                                                        .height(((iconSize + 17) / 2).dp)
                                                )
                                            }
                                            SetsCyclesNumber(
                                                Modifier
                                                    .height((iconSize + 20).dp)
                                                    .align(Alignment.BottomCenter),
                                                iconSize = iconSize,
                                                label = "sets",
                                                initialC = setsCycles
                                            ) { wrCycles ->
                                                workRestCycles = wrCycles
                                                exercisesViewModel.loadW(
                                                    warmUpD = warmUpDuration ,
                                                    workOutD = workOutDuration,
                                                    restD = restDuration,
                                                    workRestC = workRestCycles,
                                                    restBtSets = restBtSetsDuration,
                                                    setsC = setsCycles,
                                                    coolDownD = coolDownDuration
                                                )
                                            }


                                        }
                                        Intervals(
                                            modifier = Modifier
                                                .height(120.dp)
                                                .padding(
                                                    start = 10.dp,
                                                    end = 10.dp,
                                                    bottom = 30.dp
                                                ),
                                            imageId = R.drawable.resting,
                                            pDuration = restBtSetsDuration,
                                            intervalType = stringResource(R.string.rest_btw_sets)
                                        ) { restBtSetsTime ->
                                            restBtSetsDuration = restBtSetsTime
                                            exercisesViewModel.loadW(
                                                warmUpD = warmUpDuration ,
                                                workOutD = workOutDuration,
                                                restD = restDuration,
                                                workRestC = workRestCycles,
                                                restBtSets = restBtSetsDuration,
                                                setsC = setsCycles,
                                                coolDownD = coolDownDuration
                                            )
                                        }


                                    }

                                }
                                Spacer(
                                    modifier = Modifier
                                        .background(Color.Green)
                                        .height(((iconSize + 17) / 2).dp)
                                )

                            }
                            SetsCyclesNumber(
                                modifier = Modifier
                                    // .padding(end = 44.dp)
                                    .height((iconSize + 20).dp)

                                    .align(Alignment.BottomCenter),
                                label = "cycles",
                                borderColor = Color.Blue,
                                initialC = setsCycles
                            ) { setNumber ->
                                setsCycles = setNumber
                                exercisesViewModel.loadW(
                                    warmUpD = warmUpDuration ,
                                    workOutD = workOutDuration,
                                    restD = restDuration,
                                    workRestC = workRestCycles,
                                    restBtSets = restBtSetsDuration,
                                    setsC = setsCycles,
                                    coolDownD = coolDownDuration
                                )
                            }


                        }


                        Intervals(
                            modifier = Modifier
                                .height(120.dp)
                                .padding(horizontal = 10.dp),
                            imageId = R.drawable.cool_down,
                            pDuration = coolDownDuration,
                            intervalType = stringResource(R.string.cool_down)
                        ) { coolDownTime ->
                            coolDownDuration = coolDownTime

                            exercisesViewModel.loadW(
                                warmUpD = warmUpDuration ,
                                workOutD = workOutDuration,
                                restD = restDuration,
                                workRestC = workRestCycles,
                                restBtSets = restBtSetsDuration,
                                setsC = setsCycles,
                                coolDownD = coolDownDuration
                            )
                        }


                    }

                }

                Configuration.ORIENTATION_LANDSCAPE -> {
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .verticalScroll(rememberScrollState())
                    )
                    {
                        Intervals(
                            modifier = Modifier
                                .height(MaterialTheme.dimens.cardStarHeight)
                                .padding(3.dp),
                            imageId = R.drawable.warmup,
                            pDuration = warmUpDuration ,
                            intervalType = stringResource(R.string.warm_up)
                        ) { warmUpTime ->
                            warmUpDuration  = warmUpTime
                            exercisesViewModel.loadW(
                                warmUpD = warmUpDuration ,
                                workOutD = workOutDuration,
                                restD = restDuration,
                                workRestC = workRestCycles,
                                restBtSets = restBtSetsDuration,
                                setsC = setsCycles,
                                coolDownD = coolDownDuration
                            )
                        }




                        Intervals(
                            modifier = Modifier
                                .height(MaterialTheme.dimens.cardStarHeight)
                                .padding(3.dp),
                            imageId = R.drawable.dumbbell,
                            pDuration = workOutDuration,
                            intervalType = stringResource(R.string.work)
                        ) { workOutTime ->
                            workOutDuration = workOutTime
                            exercisesViewModel.loadW(
                                warmUpD = warmUpDuration ,
                                workOutD = workOutDuration,
                                restD = restDuration,
                                workRestC = workRestCycles,
                                restBtSets = restBtSetsDuration,
                                setsC = setsCycles,
                                coolDownD = coolDownDuration
                            )
                        }



                        Intervals(
                            modifier = Modifier
                                .height(MaterialTheme.dimens.cardStarHeight)
                                .padding(3.dp),
                            imageId = R.drawable.rest,
                            pDuration = restDuration,
                            intervalType = stringResource(R.string.rest)
                        ) { restTime ->
                            restDuration = restTime
                            exercisesViewModel.loadW(
                                warmUpD = warmUpDuration ,
                                workOutD = workOutDuration,
                                restD = restDuration,
                                workRestC = workRestCycles,
                                restBtSets = restBtSetsDuration,
                                setsC = setsCycles,
                                coolDownD = coolDownDuration
                            )
                        }


                        RepeatNumber(
                            modifier = Modifier .height(MaterialTheme.dimens.cardStarHeight).padding(3.dp),
                            imageId = R.drawable.workrest,
                            label = stringResource(R.string.work_rest_number),
                            initialC = workRestCycles
                        ) {

                                wrCycles ->
                            workRestCycles = wrCycles

                            exercisesViewModel.loadW(
                                warmUpD = warmUpDuration ,
                                workOutD = workOutDuration,
                                restD = restDuration,
                                workRestC = workRestCycles,
                                restBtSets = restBtSetsDuration,
                                setsC = setsCycles,
                                coolDownD = coolDownDuration
                            )


                        }

                        Intervals(
                            modifier = Modifier
                                .height(MaterialTheme.dimens.cardStarHeight)
                                .padding(3.dp),
                            imageId = R.drawable.resting,
                            pDuration = restBtSetsDuration,
                            intervalType = stringResource(R.string.rest_btw_sets)
                        ) { restBtSetsTime ->
                            restBtSetsDuration = restBtSetsTime
                            exercisesViewModel.loadW(
                                warmUpD = warmUpDuration ,
                                workOutD = workOutDuration,
                                restD = restDuration,
                                workRestC = workRestCycles,
                                restBtSets = restBtSetsDuration,
                                setsC = setsCycles,
                                coolDownD = coolDownDuration
                            )
                        }



                        RepeatNumber(
                            modifier = Modifier
                                .height(MaterialTheme.dimens.cardStarHeight)
                                .padding(3.dp),
                            imageId = R.drawable.repeat,
                            label = stringResource(R.string.sets_number),
                            initialC = setsCycles
                        ) { setNumber ->
                            setsCycles = setNumber
                            exercisesViewModel.loadW(
                                warmUpD = warmUpDuration ,
                                workOutD = workOutDuration,
                                restD = restDuration,
                                workRestC = workRestCycles,
                                restBtSets = restBtSetsDuration,
                                setsC = setsCycles,
                                coolDownD = coolDownDuration
                            )
                        }



                        Intervals(
                            modifier = Modifier
                                .height(MaterialTheme.dimens.cardStarHeight)
                                .padding(3.dp),
                            imageId = R.drawable.cool_down,
                            pDuration = coolDownDuration,
                            intervalType = stringResource(R.string.cool_down)
                        ) { coolDownTime ->
                            coolDownDuration = coolDownTime
                            exercisesViewModel.loadW(
                                warmUpD = warmUpDuration ,
                                workOutD = workOutDuration,
                                restD = restDuration,
                                workRestC = workRestCycles,
                                restBtSets = restBtSetsDuration,
                                setsC = setsCycles,
                                coolDownD = coolDownDuration
                            )
                        }


                    }

                }


            }


        }
    }
  }


//@Preview
@Composable
fun SetsCyclesNumber(

    modifier: Modifier=Modifier,iconSize:Int =40,
    label:String="sets",borderColor: Color=Color.Red,
            initialC:Int=1,
    repeatValue : (Int) -> Unit={}

){

    val range = 1..100
    var cycleNumbers  by remember { (mutableIntStateOf(initialC)) }
    repeatValue(cycleNumbers)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5 .dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(iconSize.dp), onClick = {

                cycleNumbers = if (cycleNumbers > 1) cycleNumbers - 1 else 1
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = AppColors.verdigris,
                contentColor = Color.White
            )
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Rounded.Remove,
                contentDescription = "Minus"
            )

        }

        OutlinedTextField(modifier = Modifier
            .width(60.dp)
            .wrapContentHeight(),
            value = cycleNumbers .toString(),
            onValueChange = {
               cycleNumbers=it.toIntOrNull() ?: 0

            },
            colors = OutlinedTextFieldDefaults.colors(

                unfocusedContainerColor = Color(0xFFE3E3E3),
                focusedContainerColor = Color(0xFFE1DCDA),
                unfocusedBorderColor = borderColor,
                focusedBorderColor = borderColor
            ),
            textStyle = TextStyle(textAlign = TextAlign.Center),
            label = { Text(text = label, color = borderColor, fontSize = 8.sp) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),


        )



        IconButton(
            modifier = Modifier.size(iconSize.dp), onClick = {
                if (cycleNumbers < range.last) cycleNumbers += 1
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = AppColors.SafetyOrange,
                contentColor = Color.White
            )


        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add"
            )

        }

    }
}

@Composable
@Preview(showBackground = true)
fun ShowStart(
    iconSize:Int =40

){
    Column(modifier = Modifier
          )
    {


    Intervals(modifier= Modifier
        .height(150.dp)
        .padding(5.dp),
   imageId = R.drawable.warmup,pDuration =123, intervalType =stringResource(R.string.warm_up))

        Box {
            Column {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .border(
                            BorderStroke(3.dp, color = Color.Blue),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {


                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier.padding(
                                 7.dp
                            )
                        )
                        {


                            Column {

                                Box(

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            BorderStroke(3.dp, color = Color.Red),
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                ) {
                                    Column(
                                        Modifier
                                            .padding(bottom = (iconSize / 2 + 5).dp)
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Intervals(modifier = Modifier
                                            .height(140.dp)
                                            .padding(10.dp),imageId = R.drawable.dumbbell, pDuration = 7555 , intervalType = stringResource(
                                            R.string.work
                                        )
                                        )
                                        Intervals(modifier = Modifier
                                            .height(140.dp)
                                            .padding(10.dp),imageId = R.drawable.rest,pDuration = 457554, intervalType = stringResource(
                                            R.string.rest
                                        )
                                        )



                                    }
                                }
                                Spacer(
                                    modifier = Modifier
                                        .background(Color.Green)
                                        .height(((iconSize + 17) / 2).dp)
                                )
                            }
                            SetsCyclesNumber(
                                Modifier
                                    // .padding(end = 29.dp)
                                    .height((iconSize + 20).dp)

                                    .align(Alignment.BottomCenter), iconSize = iconSize,label = "sets")

                        }
 Intervals(modifier = Modifier
     .height(140.dp)
     .padding(start = 10.dp, end = 10.dp, bottom = 35.dp) ,imageId = R.drawable.resting, pDuration = 8575, intervalType = stringResource(
                            R.string.rest_btw_sets
                        )
                        )


                    }

                }
                Spacer(
                    modifier = Modifier
                        .background(Color.Green)
                        .height(((iconSize + 17) / 2).dp)
                )

            }
            SetsCyclesNumber(modifier = Modifier
                // .padding(end = 44.dp)
                .height((iconSize + 20).dp)

                .align(Alignment.BottomCenter), label = "cycles", borderColor = Color.Blue)


        }


        Intervals(  modifier = Modifier
            .height(120.dp)
            .padding(horizontal = 10.dp), imageId = R.drawable.cool_down, pDuration = 3242, intervalType = stringResource(  R.string.cool_down  )
        )




    }



    }




