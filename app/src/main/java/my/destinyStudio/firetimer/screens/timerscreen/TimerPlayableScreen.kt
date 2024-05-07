package my.destinyStudio.firetimer.screens.timerscreen

 
 import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import my.destinyStudio.firetimer.navigation.Screens


@SuppressLint("StateFlowValueCalledInComposition")
@PreviewScreenSizes


  @Preview
 @Composable
 fun TimerBScreen(
    navController: NavController = NavController(context = LocalContext.current),
    timerBViewModel: TimerBViewModel = hiltViewModel(),


    ){

    val outerL by timerBViewModel.outer.collectAsState()
    val innerL by timerBViewModel.inner.collectAsState()
    val list by timerBViewModel.playingWorkout.collectAsState()
    val timeLeft by timerBViewModel.curTe.collectAsState()
    val i by timerBViewModel.index.collectAsState()
    val isForward by timerBViewModel.isN.collectAsState()
    val showAlert  by timerBViewModel.showAlert.collectAsState()



    val isTimerRunning by timerBViewModel.isTimerRunning.collectAsState()

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

     var isLocked by rememberSaveable { mutableStateOf(false) }



    var exitAlert   by remember { mutableStateOf(false) }
    val exerciseName by remember {mutableStateOf("Pull Ups")}



    LaunchedEffect(Unit) {

   timerBViewModel.playingWorkout.collect { workout -> // Collect the flow
            if (workout != null) {

                timerBViewModel.startTimer() // Start timer if workout is not null
            }
        }
    }


    if (showAlert) {
        LaunchedEffect(Unit) {

            delay(3000) // Show alert for 3 seconds

            navController.popBackStack(route =Screens.StartScreen.route,inclusive = false)
            timerBViewModel.resetTimer()
    }
        AlertDialog(
            onDismissRequest = {
                timerBViewModel.resetTimer()
                navController.popBackStack(route =Screens.StartScreen.route,inclusive = false)},
            title = { Text("Alert Dialog") },
            text = { Text("This is an alert dialog.") },
            confirmButton = {
                Button(onClick = {
                    timerBViewModel.resetTimer()
                    navController.popBackStack(route =Screens.StartScreen.route,inclusive = false)
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
                    navController.popBackStack(route =Screens.StartScreen.route,inclusive = false)
                    timerBViewModel.resetTimer()

                }) {
                    Text("Yes")
                }
            }
        )
    }





    BackHandler {

        exitAlert=true
               }


  Surface(modifier = Modifier
          .fillMaxSize()
          ){


      when (configuration.orientation) {



          Configuration.ORIENTATION_PORTRAIT -> {
              ConstraintLayout(modifier = Modifier
                  .fillMaxSize()) {
                  val (ppButton,bottomCard,lockButton,timerLines,textTimer,remainingTimer,wNameT )=createRefs()


                  Box(modifier = Modifier.constrainAs(remainingTimer) {

                      top.linkTo(parent.top, margin =5.dp)
                      start.linkTo(ppButton.end)
                      end.linkTo(lockButton.start)
                  }) {
                      SlideCounter(count = i+1, size = list?.workOutPrimaryDetail?.size?: 0, isPlus = isForward)
                  }
////
                  Text(modifier = Modifier.constrainAs(wNameT){
                      bottom.linkTo(timerLines.top, margin = 10.dp)
                      start.linkTo(parent.start)
                      end.linkTo(parent.end)


                  },text =exerciseName, fontSize = 37.sp)

                  IconButton(modifier = Modifier.constrainAs(ppButton){
                      top.linkTo( parent.top, margin =5.dp )
                      start.linkTo(parent.start)
                  }, onClick = {
                      if(isTimerRunning)timerBViewModel.pauseTimer()
                      else if(!isTimerRunning)   timerBViewModel.startTimer()


                  }

                      , enabled =  !isLocked
                  ) {
                      Icon(modifier = Modifier.fillMaxSize(),imageVector =  if (isTimerRunning) {Icons.Filled.Pause}
                      else if(!isTimerRunning)  {Icons.Filled.PlayArrow  }
                      else { Icons.Filled.Pause}
                          , contentDescription = "")
                  }


                  IconButton(modifier = Modifier.constrainAs(lockButton){
                      top.linkTo( parent.top, margin =5.dp)
                      end.linkTo(parent.end)
                  }, onClick = {  isLocked = !isLocked}) {
                      Icon(modifier = Modifier.fillMaxSize(),
                          imageVector = if (isLocked) {Icons.Filled.Lock}
                      else if(!isLocked){  Icons.Outlined.LockOpen}
                      else   { Icons.Filled.Lock}
                          , contentDescription = "")
                  }

                  Box(modifier=Modifier.constrainAs(textTimer){centerTo(parent)}) { TimeText(timeLeft = timeLeft)  }

                  Box(modifier=Modifier.constrainAs(timerLines){centerTo(parent)}){ Arcs(sizeArc = 320.dp, strokeWidth = 27, valueInner = innerL, valueOuter = outerL   )      }


                  Card(
                      Modifier
                          .fillMaxWidth()
                          .constrainAs(bottomCard) {
                              start.linkTo(parent.start)
                              end.linkTo(parent.end)
                              bottom.linkTo(parent.bottom)
                          }, colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                  ) {
                      Row(verticalAlignment = Alignment.CenterVertically){

                          IconButton(onClick = { timerBViewModel.previousInterval()  },
                              enabled =     i!=0   && !isLocked   ) {
                              Icon(modifier = Modifier.fillMaxSize(),imageVector=Icons.Rounded.KeyboardDoubleArrowLeft,contentDescription ="")}

                          Box(modifier=Modifier.weight(1f)){ExercisesIndicator(index=i,listOfExercise=list?.workOutPrimaryDetail?: listOf(),isPlus=isForward, cardHeight = 50)}


                          IconButton(onClick = {
                            timerBViewModel.nextInterval()
                          },
                              enabled =   i!=list?.workOutPrimaryDetail?.lastIndex && !isLocked
                              ,

                          ){
                              Icon(modifier = Modifier.fillMaxSize(), imageVector = Icons.Rounded.KeyboardDoubleArrowRight, contentDescription ="")

                          }
                      }

                  }
              }
          }
          Configuration.ORIENTATION_LANDSCAPE -> {
              ConstraintLayout(modifier = Modifier
                  .fillMaxSize()) {
                  val (ppButton,bottomCard,lockButton,timerLines,textTimer,remainingTimer,wNameT )=createRefs()


                  Box(modifier = Modifier.constrainAs(remainingTimer) {

                      top.linkTo(parent.top, margin =5.dp)
                      start.linkTo(ppButton.end)
                      end.linkTo(lockButton.start)
                  }) {
                      SlideCounter(count = i+1, size = list?.workOutPrimaryDetail?.size?:0, isPlus = isForward)
                  }
////
                  Text(modifier = Modifier.constrainAs(wNameT){
                      bottom.linkTo(textTimer.top, margin = 10.dp)
                      start.linkTo(parent.start)
                      end.linkTo(parent.end)


                  },text =exerciseName, fontSize = 30.sp)

                  IconButton(modifier = Modifier.constrainAs(ppButton){
                      top.linkTo( parent.top, margin =5.dp )
                      start.linkTo(parent.start)
                  }, onClick = {
                      if(isTimerRunning)timerBViewModel.pauseTimer()
                      else if(!isTimerRunning)   timerBViewModel.startTimer()


                  }

                      , enabled =  !isLocked
                  ) {
                      Icon(modifier = Modifier.fillMaxSize(),imageVector =  if (isTimerRunning) {Icons.Filled.Pause}
                      else if(!isTimerRunning)  {Icons.Filled.PlayArrow  }
                      else { Icons.Filled.Pause}
                          , contentDescription = "")
                  }


                  IconButton(modifier = Modifier.constrainAs(lockButton){
                      top.linkTo( parent.top, margin =5.dp)
                      end.linkTo(parent.end)
                  }, onClick = {  isLocked = !isLocked}) {
                      Icon(modifier = Modifier.fillMaxSize(),
                          imageVector = if (isLocked) {Icons.Filled.Lock}
                          else if(!isLocked){  Icons.Outlined.LockOpen}
                          else   { Icons.Filled.Lock}
                          , contentDescription = "")
                  }

                  Box(modifier=Modifier.constrainAs(textTimer){
                      bottom.linkTo(timerLines.top, margin = 10.dp)
                      start.linkTo(parent.start)
                      end.linkTo(parent.end)}) { TimeText(timeLeft = timeLeft)  }

                  Box(modifier=Modifier .constrainAs(timerLines){
                      bottom.linkTo(bottomCard.top, margin = 5.dp)
                      start.linkTo(parent.start)
                      end.linkTo(parent.end)


                  }){ Lines( strokeWidth = 15.dp, canvasWidth = screenWidth, valueUpper = innerL, valueDown =  outerL   )      }


                  Card(
                      Modifier
                          .fillMaxWidth()
                          .constrainAs(bottomCard) {
                              start.linkTo(parent.start)
                              end.linkTo(parent.end)
                              bottom.linkTo(parent.bottom)
                          }, colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                  ) {
                      Row(verticalAlignment = Alignment.CenterVertically){

                          IconButton(onClick = { timerBViewModel.previousInterval()  },
                              enabled =     i!=0   && !isLocked   ) {
                              Icon(modifier = Modifier.fillMaxSize(),imageVector=Icons.Rounded.KeyboardDoubleArrowLeft,contentDescription ="")}

                          Box(modifier=Modifier.weight(1f)){ExercisesIndicator(index=i,listOfExercise=list?.workOutPrimaryDetail?: listOf(),isPlus=isForward, cardHeight = 30 )}


                          IconButton(onClick = {
                              timerBViewModel.nextInterval()
                          },
                              enabled =   i!=list?.workOutPrimaryDetail?.lastIndex && !isLocked
                              ,

                              ){
                              Icon(modifier = Modifier.fillMaxSize(), imageVector = Icons.Rounded.KeyboardDoubleArrowRight, contentDescription ="")

                          }
                      }

                  }
              }


          }
          else -> {



              }

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
    isPlus:Boolean = true
) {
    var previousCount by remember {
        mutableIntStateOf(count)
    }
    SideEffect {
        previousCount = count
    }
    Row(modifier = modifier) {
        val cString = count.toString()
        val oldCountString = previousCount .toString()

        for(i in cString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = cString[i]
            val char = if(oldChar == newChar) {
                oldCountString[i]
            } else {
                cString[i]
            }
            AnimatedContent(
                targetState = char,
   transitionSpec = {   slideInVertically { if (isPlus) it else if(!isPlus) -it else it } togetherWith slideOutVertically {
       if (isPlus) -it else if(!isPlus) it else -it }
                }, label = ""
            ) {
                c -> Text( text = "$c", style = style,  softWrap = false, fontSize = 38.sp  )


            }
        }
        Text(  text = " / $size " , style = style, softWrap = false, fontSize = 38.sp )
    }
}
    


