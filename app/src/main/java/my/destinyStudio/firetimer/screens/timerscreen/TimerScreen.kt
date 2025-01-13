package my.destinyStudio.firetimer.screens.timerscreen


import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import my.destinyStudio.firetimer.R
import my.destinyStudio.firetimer.navigation.StartScreen
import my.destinyStudio.firetimer.navigation.TimerAScreen
import my.destinyStudio.firetimer.screens.settingScreen.SettingsViewModel
import my.destinyStudio.firetimer.ui.theme.dimens


//@Preview

@Composable
fun TimerScreen(navController: NavHostController,
                timerAViewModel: TimerAViewModel = hiltViewModel(),
                settingViewModel: SettingsViewModel = hiltViewModel(),
                identifier: String?) {

    val settings                  by settingViewModel.settings.collectAsState()
    val outerL by timerAViewModel.outer.collectAsState()
    val innerL by timerAViewModel.inner.collectAsState()
    val list by timerAViewModel.playingWorkout.collectAsState()
    val timeLeft by timerAViewModel.curTe.collectAsState()
    val i by timerAViewModel.index.collectAsState()
    val isForward by timerAViewModel.isN.collectAsState()
    val showAlert  by timerAViewModel.showAlert.collectAsState()
    val isTimerRunning by timerAViewModel.isTimerRunning.collectAsState()

    var isWorkoutLoaded  by rememberSaveable { mutableStateOf(true) }
    var isLocked by rememberSaveable { mutableStateOf(false) }
    val  sWidth by rememberSaveable { mutableIntStateOf(30) }
    val  lWidth by rememberSaveable { mutableIntStateOf(25 ) }
    var exitAlert   by rememberSaveable { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val activity = context as  Activity


//     var showFloatingTimer by rememberSaveable { mutableStateOf(false) }
//    val lifecycleOwner = LocalLifecycleOwner.current


//    DisposableEffect(lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//            if (  event == Lifecycle.Event.ON_PAUSE) {
//                showFloatingTimer = true
//
//
//     Intent(context, FloatingTimerService::class.java).also {
//         it.action = FloatingTimerService.Actions.START.toString()
//        context. startService(it)
//
//         Log.d("D","Service Launched ")
//
//                 }
//                  Log.d("D","To the for Ground")
//
//            } else if (event == Lifecycle.Event.ON_RESUME || event == Lifecycle.Event.ON_START) {
//                showFloatingTimer = false
//                Intent(context, FloatingTimerService::class.java).also {
//                    it.action = FloatingTimerService.Actions.STOP.toString()
//                    context.startService(it)
//
//    Log.d("D","Services stopped ")
//                }
//                Log.d("D","To the for Ground")
//            }
//        }
//
//        lifecycleOwner.lifecycle.addObserver(observer)
//        onDispose {
//            Intent(context, FloatingTimerService::class.java).also {
//                it.action = FloatingTimerService.Actions.STOP.toString()
//                context.startService(it)
//
//                Log.d("D","Services stopped ")
//                  }
//            lifecycleOwner.lifecycle.removeObserver(observer)
//            activity.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        }
//    }

    if (isTimerRunning) {  activity.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) }
    else { activity.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) }



    LaunchedEffect( key1 = identifier, key2 = isWorkoutLoaded) {
        Log.d("D","ReLaunched")
        Log.d("D","${configuration.screenHeightDp}")
        Log.d("D","${configuration.screenWidthDp}")
        timerAViewModel.ticModify(settings.tic )
        identifier?.let { timerAViewModel.retrieveW(it) }

        isWorkoutLoaded = true
        timerAViewModel.playingWorkout.collect {
                workout ->  if (workout != null) {
            timerAViewModel.startTimer()
        }
        }

    }



    if (showAlert) {
        LaunchedEffect(Unit) {
            delay(3000) // Show alert for 3 seconds
            navController.navigate(  StartScreen ,
                navOptions { popUpTo(TimerAScreen("")){inclusive=true} }  )
          //  timerAViewModel.resetTimer()
        }

        AlertDialog(
            onDismissRequest = {
                timerAViewModel.resetTimer()
                navController.navigate(  StartScreen ,
                    navOptions { popUpTo(TimerAScreen("")){inclusive=true} } )},
            title = { Text("Alert Dialog") },
            text = { Text("This is an alert dialog.") },
            confirmButton = {
                Button(onClick = {
                    timerAViewModel.resetTimer()
                    navController.navigate(   StartScreen,
                        navOptions { popUpTo(TimerAScreen("")){inclusive=true} }   )
                }) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }

    if (exitAlert) {
        timerAViewModel.pauseTimer()
        AlertDialog(
            onDismissRequest = {
                exitAlert = false
                timerAViewModel.startTimer()
                        },
            title = { Text(stringResource(R.string.are_sure_you_want_to_exit)) },

            dismissButton = {
                Button(onClick = {
                    exitAlert = false
                    timerAViewModel.startTimer()


                }) {
                    Text(stringResource(R.string.no))
                }
            },
            confirmButton = {
                Button(onClick = {
                    navController.navigate(  StartScreen ,
                        navOptions { popUpTo(TimerAScreen("")){inclusive=true} }  )
                    timerAViewModel.resetTimer()

                }) {
                    Text(stringResource(R.string.yes))
                }
            }
        )
    }


    BackHandler { exitAlert=true }

        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when {
                configuration.orientation==Configuration.ORIENTATION_LANDSCAPE &&configuration.screenHeightDp<480 -> {

  Row(modifier = Modifier.fillMaxSize()) {

                       ExercisesIndicator(modifier = Modifier
                           .zIndex(1f)
                           .background(color = Color.White)
                           .padding(horizontal = 7.dp)
                           .width((configuration.screenWidthDp / 2 - 150).dp)
                           .fillMaxHeight()   ,
                           index = i,
                           listOfExercise = list?.workOutPrimaryDetail ?: listOf(),
                           isPlus = isForward
                       )
                       Box(modifier = Modifier
                           .weight(0.8f)
                           .zIndex(0f)
                           ,contentAlignment = Alignment.Center ) {


                           androidx.compose.animation.AnimatedVisibility(
                            modifier = Modifier  ,
                            visible = list?.workOutPrimaryDetail?.getOrNull(i)?.uri != null,
                               enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { if (isForward) it else if (!isForward) -it else it } + fadeIn(),
                           exit = slideOutHorizontally { if (isForward) -it else if (!isForward) it else -it } + fadeOut()
//                            enter = fadeIn(),
//                            exit = fadeOut()
                        ) {
                               Column(
                                   //modifier = Modifier.border(width = 2.dp, color = Color.Red) ,
                                   horizontalAlignment = Alignment.CenterHorizontally) {
                                   TimeText( timeLeft = timeLeft, textSize = 28)
                                   AnimatedContent(
                                       modifier = Modifier.weight(1f),
                                       targetState = list?.workOutPrimaryDetail?.getOrNull(i),
                                       transitionSpec = {
                                           (slideInHorizontally(animationSpec = tween(durationMillis = 500)) { if (isForward) it else if (!isForward) -it else it } + fadeIn()).togetherWith(
                                               slideOutHorizontally { if (isForward) -it else if (!isForward) it else -it } + fadeOut())

                                       }, label = "Images"
                                   ) {   AsyncImage(
                                           model = ImageRequest.Builder(context)
                                               .data(it?.uri)
                                               .build(),
                                           contentScale = ContentScale.Fit,
                                           contentDescription = "Selected Image",
                                           error = painterResource(id = R.drawable.image_fail)
                                       )


                                   }


                                   BoxWithConstraints {

                                           Lines(
                                               strokeWidth = lWidth.dp,
                                               canvasWidth = this.maxWidth,
                                               valueDown = outerL,
                                               valueUpper = innerL,

                                           )
                                       }
                               }


                        }





                           androidx.compose.animation.AnimatedVisibility(
                            modifier = Modifier ,
                            visible = list?.workOutPrimaryDetail?.getOrNull(i)?.uri == null,
                            enter = slideInHorizontally(animationSpec = tween(durationMillis = 500)) { if (isForward) it else if (!isForward) -it else it },
                            exit = slideOutHorizontally { if (isForward) -it else if (!isForward) it else -it }
                        ) {

                               BoxWithConstraints (   contentAlignment = Alignment.Center) {

                                TimeText(
                                    timeLeft = timeLeft, textSize =MaterialTheme.dimens.timerLeftText

                                )

       Arcs(
           sizeArc =  (this.maxHeight.value.toInt()  -(sWidth+10) ).dp
           ,  strokeWidth = sWidth,
                                    valueInner = innerL,
                                    valueOuter = outerL
                                )
                            }
                        }






                    }

                Column(modifier = Modifier
                    .zIndex(1f)
                    .background(color = Color.White)
                    .border(width = 1.dp, color = Color.Gray) , horizontalAlignment = Alignment.CenterHorizontally) {
                    SlideCounter(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 5.dp),

                        count = i + 1,
                        size = list?.workOutPrimaryDetail?.size ?: 0,
                        fontSize = 25,
                        isPlus = isForward
                    )




                    IconButton(modifier = Modifier.weight(1f)
   ,
                        onClick = { if (isTimerRunning) timerAViewModel.pauseTimer() else if (!isTimerRunning) timerAViewModel.startTimer() },
                        enabled = !isLocked) {
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


                    IconButton(modifier = Modifier.weight(1f),
                        onClick = { timerAViewModel.previousInterval() },
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
                            timerAViewModel.nextInterval()
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
            else ->{
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                    val (ppButton, bottomCard, lockButton, timerLines2, textTimer, counter, noImage, yesImage) = createRefs()

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
                        onClick = { if (isTimerRunning) timerAViewModel.pauseTimer() else if (!isTimerRunning) timerAViewModel.startTimer() },
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

                    AnimatedVisibility(modifier = Modifier.constrainAs(textTimer) {
                        bottom.linkTo(timerLines2.top, margin = 3.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                    },
                        visible = list?.workOutPrimaryDetail?.getOrNull(i)?.uri != null,
                        enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { if (isForward) it else if (!isForward) -it else it } + fadeIn(),
                        exit = slideOutHorizontally { if (isForward) -it else if (!isForward) it else -it } + fadeOut()
                    ) {
                        TimeText(timeLeft = timeLeft, textSize = MaterialTheme.dimens.timerLeftText)
                    }

                    AnimatedVisibility(
                        modifier = Modifier.constrainAs(timerLines2) {

                            bottom.linkTo(bottomCard.top, margin = 3.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        visible = list?.workOutPrimaryDetail?.getOrNull(i)?.uri != null,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Lines(
                            strokeWidth = lWidth.dp,
                            canvasWidth = configuration.screenWidthDp.dp,
                            valueDown = outerL,
                            valueUpper = innerL
                        )
                    }


                    AnimatedVisibility(
                        modifier = Modifier
                            //  .border(width = 2.dp, color = Color.Cyan)
                            .constrainAs(yesImage) {
                                top.linkTo(counter.bottom, margin = 3.dp)
                                bottom.linkTo(textTimer.top, margin = 3.dp)
                                start.linkTo(parent.start, margin = 5.dp)
                                end.linkTo(parent.end, margin = 5.dp)
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints

                            },
                        visible = list?.workOutPrimaryDetail?.getOrNull(i)?.uri != null,
                        enter = slideInHorizontally { if (isForward) it else if (!isForward) -it else it },
                        exit = slideOutHorizontally { if (isForward) -it else if (!isForward) it else -it }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            AnimatedContent(
                                targetState = list?.workOutPrimaryDetail?.getOrNull(i),
                                transitionSpec = {
                                    (slideInHorizontally(animationSpec = tween(durationMillis = 500)) { if (isForward) it else if (!isForward) -it else it } + fadeIn()).togetherWith(
                                        slideOutHorizontally { if (isForward) -it else if (!isForward) it else -it } + fadeOut())

                                }, label = "Images"
                            ) {
                                 AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(it?.uri)
                                        .build(),
                                    contentScale = ContentScale.Fit,
                                    contentDescription = "Selected Image",
                                    error = painterResource(id = R.drawable.image_fail)
                                )


                            }
                        }
                    }








                    AnimatedVisibility(
                        modifier = Modifier.constrainAs(noImage) {

                            top.linkTo(counter.bottom )
                            bottom.linkTo(bottomCard.top )
                            start.linkTo(parent.start )
                            end.linkTo(parent.end )
                            height = Dimension.wrapContent
                            width = Dimension.wrapContent
                        } ,
                        visible = list?.workOutPrimaryDetail?.getOrNull(i)?.uri == null,
                        enter = slideInHorizontally(animationSpec = tween(durationMillis = 500)) { if (isForward) it else if (!isForward) -it else it },
                        exit = slideOutHorizontally { if (isForward) -it else if (!isForward) it else -it }
                    ) {

                        BoxWithConstraints  (  modifier = Modifier.border(2.dp, color = Color.Green), contentAlignment = Alignment.Center  ) {
                            TimeText(
                                timeLeft = timeLeft, textSize = MaterialTheme.dimens.timerLeftText
                            )

                            Arcs(
                                sizeArc = when(configuration.orientation) {

                                    Configuration.ORIENTATION_PORTRAIT ->{(this.maxWidth.value -30 ).dp}
                                    Configuration.ORIENTATION_LANDSCAPE ->{(this.maxHeight.value-30).dp}
                                    else -> 200.dp


                                },
                                     //-  sWidth
                                strokeWidth = sWidth,
                                valueInner = innerL,
                                valueOuter = outerL
                            )
                        }
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
                                onClick = { timerAViewModel.previousInterval() },
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
                                    timerAViewModel.nextInterval()
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
        }
        }




