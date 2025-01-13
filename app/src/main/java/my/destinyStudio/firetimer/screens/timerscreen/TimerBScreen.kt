package my.destinyStudio.firetimer.screens.timerscreen

 
 import android.annotation.SuppressLint
import android.app.Activity
import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import android.util.Rational
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.rounded.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.rounded.KeyboardDoubleArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.navOptions
import kotlinx.coroutines.delay

 import my.destinyStudio.firetimer.navigation.StartScreen
import my.destinyStudio.firetimer.navigation.TimerBScreen
import my.destinyStudio.firetimer.screens.settingScreen.SettingsViewModel
import my.destinyStudio.firetimer.ui.theme.dimens


//@PreviewScreenSizes
@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Preview

@Composable
 fun TimerBScreen(
    navController: NavController = NavController(context = LocalContext.current),
    timerBViewModel: TimerBViewModel = hiltViewModel(),
    settingViewModel: SettingsViewModel = hiltViewModel()

    ){


    val settings            by settingViewModel.settings.collectAsState()
    val outerL              by timerBViewModel.outer.collectAsState()
    val innerL              by timerBViewModel.inner.collectAsState()
    val list                by timerBViewModel.playingWorkout.collectAsState()
    val timeLeft            by timerBViewModel.curTe.collectAsState()
    val i                   by timerBViewModel.index.collectAsState()
    val isForward           by timerBViewModel.isN.collectAsState()
    val showAlert           by timerBViewModel.showAlert.collectAsState()
    val isTimerRunning      by timerBViewModel.isTimerRunning.collectAsState()

    val configuration = LocalConfiguration.current
    val context = LocalContext.current as Activity
   val activity = context as? Activity

    val  sWidth by remember { mutableIntStateOf(30) }
    var isLocked by rememberSaveable { mutableStateOf(false) }
    var exitAlert   by rememberSaveable { mutableStateOf(false) }
    var  hasRun  by rememberSaveable { mutableStateOf(false) }
    var isPip by rememberSaveable { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    val aspectRatio = Rational(1, 1) // Adjust as needed
    val pipParams = PictureInPictureParams.Builder()
        .setAspectRatio( aspectRatio) 
        .build()



    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {

            isPip=true
                Log.d("D","OnPause")

            } else if (event == Lifecycle.Event.ON_RESUME || event == Lifecycle.Event.ON_START) {
                isPip=false
                  Log.d("D","OnStart onResume")
                }

            }


        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
           Log.d("D","OnDispose ")
              timerBViewModel.resetTimer()
              isPip=false
               lifecycleOwner.lifecycle.removeObserver(observer)

               activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }

        }


    if (isTimerRunning) {  activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) }
    else { activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) }



    LaunchedEffect(key1 = Unit) {

        timerBViewModel.ticModify(settings.tic )
        timerBViewModel.vibrationModify(settings.vibrationEnabled )

        if (!hasRun )  {

            timerBViewModel.startTimer()

        hasRun =true
            Log.d("D","ReLaunched B ")
        }

    }

    if (showAlert) {

            LaunchedEffect(Unit) {
                isPip=false
               // if (isPip) { context.finish()}
                delay(3000) // Show alert for 3 seconds
                navController.navigate(  StartScreen,
                    navOptions { popUpTo(TimerBScreen){inclusive=true} }  )
                timerBViewModel.resetTimer()

        }

        AlertDialog(
            onDismissRequest = {
                timerBViewModel.resetTimer()
                navController.navigate(  StartScreen  )},
            title = { Text("Alert Dialog") },
            text = { Text("This is an alert dialog.") },
            confirmButton = {
                Button(onClick = {
                    timerBViewModel.resetTimer()
                    navController.navigate(  StartScreen  )
                }) {
                    Text("OK")
                }
            }
        )
    }
    if (exitAlert) {
        timerBViewModel.pauseTimer()
        AlertDialog(
            onDismissRequest = {
                exitAlert = false
                timerBViewModel.startTimer()

                },
            title = { Text("Are Sure you want to Exit ?") },

            dismissButton = {
                Button(onClick = {
                    exitAlert = false
                    timerBViewModel.startTimer()


                }) {
                    Text("No")
                }
            },
            confirmButton = {
                Button(onClick = {
                    navController.navigate( StartScreen  )
                    timerBViewModel.resetTimer()

                }) {
                    Text("Yes")
                }
            }
        )
    }


    BackHandler {   exitAlert=true  }

    if ( isPip &&isTimerRunning){
        context.enterPictureInPictureMode(pipParams)
        FloatingTimerTest(innerL = innerL,
            outerL = outerL,
            timeLeft = timeLeft,
//            onLeftClick ={timerBViewModel.previousInterval()} ,
//            onRightClick = {timerBViewModel.nextInterval()}

        )

    }
  else
      //if (!isPip)
  {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {

            when {
                configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && configuration.screenHeightDp < 480 -> {

                    Row(modifier = Modifier.fillMaxSize()) {

                        ExercisesIndicator(
                            modifier = Modifier
                                .zIndex(1f)
                                .background(color = Color.White)
                                .padding(horizontal = 7.dp)
                                .width((configuration.screenWidthDp / 2 - 150).dp)
                                .fillMaxHeight(),
                            index = i,
                            listOfExercise = list?.workOutPrimaryDetail ?: listOf(),
                            isPlus = isForward
                        )
                        Box(
                            modifier = Modifier
                                .weight(0.8f)
                                .zIndex(0f),
                            contentAlignment = Alignment.Center
                        ) {

                            androidx.compose.animation.AnimatedVisibility(
                                modifier = Modifier,
                                visible = list?.workOutPrimaryDetail?.getOrNull(i)?.uri == null,
                                enter = slideInHorizontally(animationSpec = tween(durationMillis = 500)) { if (isForward) it else if (!isForward) -it else it },
                                exit = slideOutHorizontally { if (isForward) -it else if (!isForward) it else -it }
                            ) {

                                BoxWithConstraints(contentAlignment = Alignment.Center) {

                                    TimeText(
                                        timeLeft = timeLeft,
                                        textSize = MaterialTheme.dimens.timerLeftText

                                    )

                                    Arcs(
                                        sizeArc = (this.maxHeight.value.toInt() - (sWidth + 10)).dp,
                                        strokeWidth = sWidth,
                                        valueInner = innerL,
                                        valueOuter = outerL
                                    )
                                }
                            }


                        }

                        Column(
                            modifier = Modifier
                                .zIndex(1f)
                                .background(color = Color.White)
                                .border(width = 1.dp, color = Color.Gray),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SlideCounter(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 5.dp),

                                count = i + 1,
                                size = list?.workOutPrimaryDetail?.size ?: 0,
                                fontSize = 25,
                                isPlus = isForward
                            )




                            IconButton(
                                modifier = Modifier.weight(1f),
                                onClick = { if (isTimerRunning) timerBViewModel.pauseTimer() else if (!isTimerRunning) timerBViewModel.startTimer() },
                                enabled = !isLocked
                            ) {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    imageVector = if (isTimerRunning) {
                                        Icons.Filled.Pause
                                    } else if (!isTimerRunning) {
                                        Icons.Filled.PlayArrow
                                    } else {
                                        Icons.Filled.Pause
                                    },
                                    contentDescription = ""
                                )
                            }
                            IconButton(modifier = Modifier.weight(1f),
                                onClick = { isLocked = !isLocked }) {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    imageVector = if (isLocked) {
                                        Icons.Filled.Lock
                                    } else if (!isLocked) {
                                        Icons.Outlined.LockOpen
                                    } else {
                                        Icons.Filled.Lock
                                    }, contentDescription = ""
                                )
                            }


                            IconButton(
                                modifier = Modifier.weight(1f),
                                onClick = { timerBViewModel.previousInterval() },
                                enabled = i != 0 && !isLocked
                            ) {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    imageVector = Icons.Rounded.KeyboardDoubleArrowLeft,
                                    contentDescription = ""
                                )
                            }

                            IconButton(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    timerBViewModel.nextInterval()
                                },
                                enabled = i != list?.workOutPrimaryDetail?.lastIndex && !isLocked,

                                ) {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    imageVector = Icons.Rounded.KeyboardDoubleArrowRight,
                                    contentDescription = ""
                                )

                            }


                        }


                    }

                }

                else -> {
                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                        val (ppButton, bottomCard, lockButton, counter, noImage) = createRefs()

                        SlideCounter(
                            modifier = Modifier
                                .constrainAs(counter) {
                                    top.linkTo(parent.top, margin = 5.dp)
                                    start.linkTo(ppButton.end)
                                    end.linkTo(lockButton.start)

                                }
                                .height(MaterialTheme.dimens.timerIcons),
                            count = i + 1,
                            size = list?.workOutPrimaryDetail?.size ?: 0,
                            fontSize = MaterialTheme.dimens.sliderCounterFont,


                            isPlus = isForward
                        )



                        IconButton(
                            modifier = Modifier
                                .size(MaterialTheme.dimens.timerIcons)
                                .constrainAs(ppButton) {
                                    top.linkTo(parent.top, margin = 5.dp)
                                    start.linkTo(parent.start)

                                },
                            onClick = { if (isTimerRunning) timerBViewModel.pauseTimer() else if (!isTimerRunning) timerBViewModel.startTimer() },
                            enabled = !isLocked
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = if (isTimerRunning) {
                                    Icons.Filled.Pause
                                } else if (!isTimerRunning) {
                                    Icons.Filled.PlayArrow
                                } else {
                                    Icons.Filled.Pause
                                },
                                contentDescription = ""
                            )
                        }
                        IconButton(modifier = Modifier
                            .size(MaterialTheme.dimens.timerIcons)
                            .constrainAs(lockButton) {
                                top.linkTo(parent.top, margin = 5.dp)
                                end.linkTo(parent.end)

                            }, onClick = { isLocked = !isLocked }) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = if (isLocked) {
                                    Icons.Filled.Lock
                                } else if (!isLocked) {
                                    Icons.Outlined.LockOpen
                                } else {
                                    Icons.Filled.Lock
                                }, contentDescription = ""
                            )
                        }










                        BoxWithConstraints(modifier = Modifier
                            .constrainAs(noImage) {

                                top.linkTo(counter.bottom)
                                bottom.linkTo(bottomCard.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                height = Dimension.wrapContent
                                width = Dimension.wrapContent
                            }
                            .border(2.dp, color = Color.Green), contentAlignment = Alignment.Center) {
                            TimeText(
                                timeLeft = timeLeft, textSize = MaterialTheme.dimens.timerLeftText
                            )

                            Arcs(
                                sizeArc = when (configuration.orientation) {

                                    Configuration.ORIENTATION_PORTRAIT -> {
                                        (this.maxWidth.value - 30).dp
                                    }

                                    Configuration.ORIENTATION_LANDSCAPE -> {
                                        (this.maxHeight.value - 30).dp
                                    }

                                    else -> 200.dp


                                },
                                //-  sWidth
                                strokeWidth = sWidth,
                                valueInner = innerL,
                                valueOuter = outerL
                            )
                        }



                        Card(
                            Modifier
                                .fillMaxWidth()
                                .constrainAs(bottomCard) {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                },
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            Row(
                                modifier = Modifier.height((configuration.screenHeightDp / 4).dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                IconButton(
                                    modifier = Modifier.size(MaterialTheme.dimens.timerIcons),
                                    onClick = { timerBViewModel.previousInterval() },
                                    enabled = i != 0 && !isLocked
                                ) {
                                    Icon(
                                        modifier = Modifier.fillMaxSize(),
                                        imageVector = Icons.Rounded.KeyboardDoubleArrowLeft,
                                        contentDescription = ""
                                    )
                                }

                                Box(modifier = Modifier.weight(1f)) {
                                    ExercisesIndicator(
                                        index = i,
                                        listOfExercise = list?.workOutPrimaryDetail ?: listOf(),
                                        isPlus = isForward
                                    )
                                }


                                IconButton(
                                    modifier = Modifier.size(MaterialTheme.dimens.timerIcons),
                                    onClick = {
                                        timerBViewModel.nextInterval()
                                    },
                                    enabled = i != list?.workOutPrimaryDetail?.lastIndex && !isLocked,

                                    ) {
                                    Icon(
                                        modifier = Modifier.fillMaxSize(),
                                        imageVector = Icons.Rounded.KeyboardDoubleArrowRight,
                                        contentDescription = ""
                                    )

                                }
                            }

                        }
                    }
                }

            }


            ////


        }
    }





}










//@Preview
@Composable
fun SlideCounter(
    modifier: Modifier = Modifier,
    count: Int=12,
    size:Int =19,
    style: TextStyle = MaterialTheme.typography.titleSmall,
    fontSize:Int =38,
   // width:Dp=50.dp,
  //  height:Dp=50.dp,
    isPlus:Boolean = true
) {
    var previousCount by remember {
        mutableIntStateOf(count)
    }
    SideEffect {
        previousCount = count
    }
//       Box(  modifier = modifier.height(height).width(width) , contentAlignment = Alignment.Center )
//    {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            val cString = count.toString()
            val oldCountString = previousCount.toString()

            for (i in cString.indices) {
                val oldChar = oldCountString.getOrNull(i)
                val newChar = cString[i]
                val char = if (oldChar == newChar) {
                    oldCountString[i]
                } else {
                    cString[i]
                }
                AnimatedContent(
                    targetState = char,
                    transitionSpec = {
                        slideInVertically { if (isPlus) it else if (!isPlus) -it else it } togetherWith slideOutVertically {
                            if (isPlus) -it else if (!isPlus) it else -it
                        }
                    }, label = ""
                ) { c ->
                    Text(text = "$c", style = style, softWrap = false, fontSize = fontSize.sp)


                }
            }
            Text(text = " / $size ", style = style, softWrap = false, fontSize = fontSize.sp)
        }
   // }
}
    


