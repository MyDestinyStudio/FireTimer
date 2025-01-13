package my.destinyStudio.firetimer.screens.savedworkouts

import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import my.destinyStudio.firetimer.R
import my.destinyStudio.firetimer.data.ExercisesViewModel
import my.destinyStudio.firetimer.data.WorkOutDetail
import my.destinyStudio.firetimer.data.initial
import my.destinyStudio.firetimer.data.intervalsExp2
import my.destinyStudio.firetimer.data.wdTest
import my.destinyStudio.firetimer.navigation.EditScreen
import my.destinyStudio.firetimer.navigation.SavedWorkOutScreen
import my.destinyStudio.firetimer.navigation.ScheduleScreen
import my.destinyStudio.firetimer.navigation.TimerAScreen
import my.destinyStudio.firetimer.screens.settingScreen.SettingsViewModel
import my.destinyStudio.firetimer.ui.theme.AppColors
import my.destinyStudio.firetimer.ui.theme.dimens
import my.destinyStudio.firetimer.utils.formatToMMSS
import java.sql.Date
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SavedWorkOutCards(
    navController: NavController = NavController(context = LocalContext.current),
    exercisesViewModel: ExercisesViewModel = viewModel(),

    settingViewmodel: SettingsViewModel
){


     val workouts = exercisesViewModel.workoutList .collectAsState().value
     val currentTimes by remember {   mutableStateOf(Date.from(Instant.now()).toString())    }
     var showReview by remember {    mutableStateOf(false)  }
     var deleteWorkout by remember {    mutableStateOf(false)  }
     var selectedWorkoutToDeleteId by remember {  mutableStateOf( "")  }
     var selectedWorkoutToReview by remember {  mutableStateOf( initial)  }


    val configuration = LocalConfiguration.current
  if (showReview){
       ExercisesReviewS(workoutToReview =selectedWorkoutToReview  ){
          showReview =false
       }

    }
    if (deleteWorkout ){
        DeleteWorkoutAlertDialog(
         onDismiss ={   deleteWorkout= false  },
         onConfirm ={
         exercisesViewModel.removeWorkOutById(selectedWorkoutToDeleteId)
            deleteWorkout= false
                   }
                             )

    }

    BackHandler {
        settingViewmodel.indexPage(1)
       navController.navigate(ScheduleScreen, navOptions { popUpTo(SavedWorkOutScreen){inclusive=true} } )

        Log.d("Screens","BackHandler SavedWorkout")
    }
  Scaffold(modifier = Modifier.fillMaxSize() ,
               topBar ={
                   when {
                       configuration.orientation== Configuration.ORIENTATION_LANDSCAPE &&configuration.screenHeightDp<480 -> {

                       }
                       else -> {
                   TopAppBarA( title= stringResource(R.string.workouts, workouts.size), actionButtons =
                   listOf(Pair(Icons.Rounded.Info,"onInfo"))


                )  }
                   }
                       }  ,

      floatingActionButton = {
          FloatingActionButton(modifier = Modifier.size(MaterialTheme.dimens.floatingActionButton)
    ,shape = CircleShape,
              containerColor = AppColors.mOrange ,
     onClick = {  exercisesViewModel.addWorkOut(WorkOutDetail(workOutName = currentTimes, workOutPrimaryDetail = intervalsExp2)) }  )
          {   Icon( modifier = Modifier.fillMaxSize() ,imageVector =  Icons.Rounded.Add, contentDescription ="", tint = Color.White)  }
               }

           ) {
   paddingValues ->
               Box (modifier = Modifier
                   .fillMaxSize()
                   .padding(paddingValues)){
                   Column(
                       modifier = Modifier
                           .fillMaxSize()

                   ) {   LazyColumn(
                           modifier = Modifier.weight(1f), contentPadding = PaddingValues(
                               horizontal = 2.dp, vertical = 5.dp
                           ), verticalArrangement = Arrangement.spacedBy(7.dp)
                       ) {

                           items(workouts) { e ->
                               ExerciseCardA(
                                   workOutDetail = e,

                                   onDeleteClicked = {
                                       deleteWorkout = true
                                       selectedWorkoutToDeleteId = it
                                   },
                                   onEditClicked = {
                                       val identy = it
                                        navController.navigate(  EditScreen( identy) )

                                   },
                                   onReviewClicked = {
                                       selectedWorkoutToReview = it
                                       showReview = true

                                   },
                                   onStartClick = {
                                       Log.d("N", e.id.toString())
                                       navController.navigate(TimerAScreen(e.id.toString()))

                                   }
                               )
                           }
                       item {
                           Spacer(modifier = Modifier
                               .height(MaterialTheme.dimens.floatingActionButton + 10.dp)
                               .fillMaxWidth()
                               .background(Color.Transparent))
                       }
                       }

                   }


               }
      }

}



@Composable
 //@Preview
fun ExerciseCard(
    workOutDetail: WorkOutDetail= wdTest,
    onDeleteClicked : (String) -> Unit={},
    onEditClicked: (String) -> Unit={},
    onReviewClicked: (WorkOutDetail) -> Unit={},
    onStartClick: () -> Unit={}
) {
    Card(
        Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(200.dp),

        colors = CardDefaults.cardColors(containerColor = Color.Red)

    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 7.dp)) {

            Column(modifier = Modifier
                .weight(3f)
                ) {

                Box( modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), contentAlignment = Alignment.Center) {
                    Text(

                        text = workOutDetail.workOutName,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,

                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 30.sp


                    )
                }
                LazyColumn(modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 3.dp)) {
                    itemsIndexed(workOutDetail.workOutPrimaryDetail){
                            i,a ->
                        IntervalDots(index =i, interval = a )

                    }

                }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp))
 Row (
     Modifier
         .fillMaxWidth()
         .weight(1f), horizontalArrangement = Arrangement.Center,  verticalAlignment = Alignment.CenterVertically){


    CardInfo(modifier = Modifier.weight(1f),  title = "Intervals",  subString = workOutDetail.workOutPrimaryDetail.size.toString() )

                 Spacer(modifier = Modifier
                     .fillMaxHeight()
                     .width(7.dp))

   CardInfo(modifier = Modifier.weight(1f), title = "Duration", subString = formatToMMSS(workOutDetail.workOutPrimaryDetail.sumOf { it.intervalDuration })  )


                }

            }
            IconButton(onClick = {  onStartClick()}, modifier = Modifier
                .weight(1.8f)
                .fillMaxHeight()) {
                Icon(modifier = Modifier.fillMaxSize(), imageVector = Icons.Rounded.PlayArrow, contentDescription ="", tint = Color.White )

            }
            Column(modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight() , horizontalAlignment = Alignment.CenterHorizontally ) {
                IconButton(modifier = Modifier.weight(1f), onClick = { onEditClicked(workOutDetail.id.toString())}) {
                    Icon(modifier = Modifier.fillMaxSize(),imageVector = Icons.Filled.Edit, contentDescription = "", tint = Color.White)}

                IconButton(modifier = Modifier.weight(1f),onClick = {onDeleteClicked(workOutDetail.id.toString()) }) {
                    Icon(modifier = Modifier.fillMaxSize(),imageVector = Icons.Filled.Delete , contentDescription = "", tint = Color.White)}

   IconButton(modifier = Modifier.weight(1f),onClick = {   onReviewClicked(workOutDetail)  }) {
                    Icon(modifier = Modifier.fillMaxSize(),imageVector = Icons.Filled.RemoveRedEye, contentDescription = "", tint = Color.White)}


            }

        }



    }

}




val customShape = object : Shape {
    override fun createOutline(
        size: Size ,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val path = Path().apply {
            val normalCorner =80f
            val bigCorner =200f

            arcTo(
                rect =  Rect(offset = Offset(0f,0f    ),
                    size = Size(normalCorner,normalCorner)),
                startAngleDegrees = 180f,
                sweepAngleDegrees =  90f,
                forceMoveTo = false
            )


            arcTo(rect =  Rect(offset = Offset(size.width*3/4f-normalCorner,0f),
                size = Size(normalCorner,normalCorner)),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            //B
            // lineTo(size.width*3/4f   ,size.height/2f)
            arcTo(rect =  Rect(offset = Offset(size.width*3/4f  ,size.height/2f -bigCorner  ),
                size = Size(bigCorner,bigCorner)),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )




            arcTo(
                rect =  Rect(offset = Offset(size.width-normalCorner ,size.height/2f ),
                    size = Size(normalCorner,normalCorner)),
                startAngleDegrees = 270f,
                sweepAngleDegrees =  90f,
                forceMoveTo = false
            )

            arcTo(
                rect =  Rect(offset = Offset(size.width-normalCorner,size.height-normalCorner    ),
                    size = Size(normalCorner,normalCorner)),
                startAngleDegrees = 0f,
                sweepAngleDegrees =  90f,
                forceMoveTo = false
            )


            arcTo(
                rect =  Rect(offset = Offset(0f,size.height -normalCorner    ),
                    size = Size(normalCorner,normalCorner)),
                startAngleDegrees = 90f,
                sweepAngleDegrees =  90f,
                forceMoveTo = false
            )
            close()
           }
        return Outline.Generic(path)
    }
}


@Composable
@Preview
fun ExerciseCardA(

    workOutDetail: WorkOutDetail= wdTest,
    onDeleteClicked : (String) -> Unit={},
    onEditClicked: (String) -> Unit={},
    onReviewClicked: (WorkOutDetail) -> Unit={},
    onStartClick: () -> Unit={}
) {
    BoxWithConstraints   {

        IconButton(onClick = {  onStartClick()}, modifier = Modifier
            .align(Alignment.TopEnd)
            .size((this.maxWidth/4)-3 .dp),
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Red)) {
            Icon(modifier = Modifier.fillMaxSize(), imageVector = Icons.Rounded.PlayArrow, contentDescription ="", tint = Color.White )

        }
             Card(
            modifier = Modifier
                .width(this.maxWidth)
                .height( this.maxWidth / 2 )
                .clip(
                    customShape
                )

            ,colors = CardDefaults.cardColors(containerColor = Color.Red)

        ) {
                 Row(
                     Modifier
                         .fillMaxSize()
                         .padding(horizontal = 10.dp, vertical = 7.dp)) {

                     Column(modifier = Modifier
                         .weight(0.5f)
                     ) {

                         Box( modifier = Modifier
                             .fillMaxWidth()
                             .weight(1f), contentAlignment = Alignment.Center) {
                             Text(

                                 text = workOutDetail.workOutName,
                                 textAlign = TextAlign.Center,
                                 color = Color.White,
                                 maxLines = 1,
                                 overflow = TextOverflow.Ellipsis,

                                 style = MaterialTheme.typography.bodyLarge,
                                 fontSize = 30.sp


                             )
                         }
                         LazyColumn(modifier = Modifier
                             .weight(1.5f)
                             .padding(horizontal = 3.dp)) {
                             itemsIndexed(workOutDetail.workOutPrimaryDetail){
                                     i,a ->
                                 IntervalDots(index =i, interval = a )

                             }

                         }
                         Spacer(modifier = Modifier
                             .fillMaxWidth()
                             .height(7.dp))
                         Row (
                             Modifier
                                 .fillMaxWidth()
                                 .weight(1f), horizontalArrangement = Arrangement.Center,  verticalAlignment = Alignment.CenterVertically){


                             CardInfo(modifier = Modifier.weight(1f),  title = "Intervals",  subString = workOutDetail.workOutPrimaryDetail.size.toString() )

                             Spacer(modifier = Modifier
                                 .fillMaxHeight()
                                 .width(7.dp))

                             CardInfo(modifier = Modifier.weight(1f), title = "Duration", subString = formatToMMSS(workOutDetail.workOutPrimaryDetail.sumOf { it.intervalDuration })  )


                         }

                     }

                     Row(
                         modifier = Modifier
                             .weight(0.5f)
                             .fillMaxHeight(),

                     )  {
                         Column(
                             modifier = Modifier
                                 .weight(0.5f)
                                 .fillMaxHeight(),
                             horizontalAlignment = Alignment.CenterHorizontally
                         ) {
                             IconButton(
                                 modifier = Modifier.weight(1f),
                                 onClick = { onEditClicked(workOutDetail.id.toString()) }) {
                                 Icon(
                                     modifier = Modifier.fillMaxSize(),
                                     imageVector = Icons.Filled.Edit,
                                     contentDescription = "",
                                     tint = Color.White
                                 )
                             }

                             IconButton(
                                 modifier = Modifier.weight(1f),
                                 onClick = { onDeleteClicked(workOutDetail.id.toString()) }) {
                                 Icon(
                                     modifier = Modifier.fillMaxSize(),
                                     imageVector = Icons.Filled.Delete,
                                     contentDescription = "",
                                     tint = Color.White
                                 )
                             }




                         }
                         Column(
                             modifier = Modifier
                                 .weight(0.5f)
                                 .fillMaxHeight(),
                             horizontalAlignment = Alignment.CenterHorizontally
                         ) {
                             Box(modifier = Modifier.weight(1f))

                             IconButton(
                                 modifier = Modifier.weight(1f),
                                 onClick = { onReviewClicked(workOutDetail) }) {
                                 Icon(
                                     modifier = Modifier.fillMaxSize(),
                                     imageVector = Icons.Filled.RemoveRedEye,
                                     contentDescription = "",
                                     tint = Color.White
                                 )
                             }


                         }
                     }

                 }



             }


    }
    }



