@file:OptIn(ExperimentalMaterial3Api::class)

package my.destinyStudio.firetimer.screens.savedworkouts

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import my.destinyStudio.firetimer.data.ExercisesViewModel
import my.destinyStudio.firetimer.data.WorkOutDetail
import my.destinyStudio.firetimer.data.initial
import my.destinyStudio.firetimer.data.intervalsExp2
import my.destinyStudio.firetimer.data.wdTest
import my.destinyStudio.firetimer.navigation.Screens
import my.destinyStudio.firetimer.utils.formatToMMSS
import java.sql.Date
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SavedWorkOutCards(
    exercisesViewModel: ExercisesViewModel = viewModel(),
    navController: NavController=NavController(context = LocalContext.current)
){
     val workouts = exercisesViewModel.workoutList .collectAsState().value
     val currentTimes by remember {   mutableStateOf(Date.from(Instant.now()).toString())    }
     var showReview by remember {    mutableStateOf(false)  }
     var deleteWorkout by remember {    mutableStateOf(false)  }
     var selectedWorkoutToDeleteId by remember {  mutableStateOf( "")  }
     var selectedWorkoutToReview by remember {  mutableStateOf( initial)  }



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


           Scaffold(modifier = Modifier.fillMaxSize() ,
               topBar ={
                   TopAppBarA( title  ="Workouts" ,
     navigationIcon = {  navController.popBackStack(route =Screens.StartScreen.route,inclusive = false)},
              infoIcon = { navController.navigate(Screens.InfoScreen.route)},
              settingIcon = {navController.navigate(Screens.SettingScreen.route)}

          )      },

           ) {
   paddingValues ->
               Box (modifier = Modifier
                   .fillMaxSize()
                   .padding(paddingValues)){
                   Column(
                       modifier = Modifier
                           .fillMaxSize()

                   ) {

                       LazyColumn(
                           modifier = Modifier.weight(1f), contentPadding = PaddingValues(
                               horizontal = 2.dp, vertical = 5.dp
                           ), verticalArrangement = Arrangement.spacedBy(7.dp)
                       ) {

                           items(workouts) { e ->
                               ExerciseCard(
                                   workOutDetail = e,

                                   onDeleteClicked = {
                                       deleteWorkout = true
                                       selectedWorkoutToDeleteId = it
                                   },
                                   onEditClicked = {
                                       val identy = it
                                       navController.navigate(Screens.SavedWorkOutEditScreen.route + "/${identy}")

                                   },
                                   onReviewClicked = {
                                       selectedWorkoutToReview = it
                                       showReview = true

                                   },
                                   onStartClick = {
                                       Log.d("N", e.id.toString())
                                       navController.navigate(Screens.TimerScreen.route + "/${e.id}")

                                   }
                               )
                           }
                       }
                        InstructionsCard()
                   }

             FloatingActionButton(
                     modifier =
                     Modifier
                         .size(70.dp)
                         .align(Alignment.BottomEnd)
                         .padding(end = 3.dp, bottom = 3.dp) ,


                 onClick = {
                exercisesViewModel.addWorkOut(WorkOutDetail(workOutName = currentTimes, workOutPrimaryDetail = intervalsExp2))

                  },
                           shape = CircleShape,
                           containerColor = Color.Cyan,
                           elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(
                               defaultElevation = 15.dp,
                               pressedElevation = 30.dp
                           )
                       ) {
                           Icon(imageVector = Icons.Filled.Add, contentDescription ="" )
                       }



               }
      }

}



@Composable
 @Preview
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
            .height(180.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Red)

    ) {
        Row(Modifier.fillMaxSize()) {

            Column(modifier = Modifier
                .weight(3f)
                .padding(bottom = 7.dp, top = 4.dp)) {

                Box( modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), contentAlignment = Alignment.Center) {
                    Text(
                   //     modifier = Modifier.padding(horizontal = 10.dp) ,
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
                    .padding(start = 3.dp)) {
                    itemsIndexed(workOutDetail.workOutPrimaryDetail){
                            i,a ->
                        IntervalDots(index =i, interval = a )

                    }

                }
                Spacer(modifier = Modifier.fillMaxWidth().height(7.dp))
 Row (
     Modifier
         .fillMaxWidth()
         .weight(1f), horizontalArrangement = Arrangement.Center,  verticalAlignment = Alignment.CenterVertically){

     Spacer(modifier = Modifier
         .fillMaxHeight()
         .width(7.dp))
     Box (modifier = Modifier.weight(1f)){
         CardInfo(
             title = "Intervals",
             subString = workOutDetail.workOutPrimaryDetail.size.toString()
         )
     }
                 Spacer(modifier = Modifier
                     .fillMaxHeight()
                     .width(7.dp))
     Box (modifier = Modifier.weight(1f)){
         CardInfo(
             title = "Duration",
             subString = formatToMMSS(workOutDetail.workOutPrimaryDetail.sumOf { it.intervalDuration })
         )
     }

                }

            }
            IconButton(onClick = {  onStartClick()}, modifier = Modifier
                .weight(1.5f)
                .fillMaxHeight()) {
                Icon(modifier = Modifier.fillMaxSize(), imageVector = Icons.Rounded.PlayArrow, contentDescription ="", tint = Color.White )

            }
            Column(modifier = Modifier
                .weight(1f)
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
