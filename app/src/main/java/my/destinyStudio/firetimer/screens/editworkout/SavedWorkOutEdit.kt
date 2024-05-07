package my.destinyStudio.firetimer.screens.editworkout

 
import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import my.destinyStudio.firetimer.components.IntervalsType
import my.destinyStudio.firetimer.data.IntervalsInfoIndexed
import my.destinyStudio.firetimer.navigation.Screens
import my.destinyStudio.firetimer.ui.theme.AppColors


@SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableState")
@Composable

fun SavedWorkOutEditScreen(
    navController: NavController=NavController(context = LocalContext.current),
   identifier:String,
    editWorkoutViewModel: EditWorkoutViewModel= viewModel( key = "workout_edit_$identifier" ),
){


    val workoutState by editWorkoutViewModel.workoutUi.collectAsState()


    val totalDuration by editWorkoutViewModel.totalDuration.collectAsState()

    val intervalsNumber by  editWorkoutViewModel.intervalsNumber.collectAsState()

    val setsNumber  by  editWorkoutViewModel.setsNumber.collectAsState()

    var showReview by remember { mutableStateOf(false) }

    var showNameDialog by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()


    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        onDispose {
            editWorkoutViewModel.onCleared()
            Log.d("T","Edit Dipose " )
        }
    }

    BackHandler {
        navController.popBackStack(route = Screens.SavedWorkOutScreen.route ,inclusive = false)


    }



    LaunchedEffect(key1 = identifier) {
        lifecycleOwner.lifecycleScope.launch {
            if (identifier!=editWorkoutViewModel.workout.value?.id.toString()) {
                editWorkoutViewModel.getWByIdd(identifier)
            }


            Log.d("A", "Launched Effect,$identifier")
        }
    }


//    var scrollTo by remember {
//    mutableIntStateOf(0)
//}
//    LaunchedEffect(key1 = scrollTo){
//        listState.animateScrollToItem(scrollTo)
//    }


    if (showNameDialog) {
        NameChangeAlertDialog(   initialName = workoutState.nameW,  onDismiss = { showNameDialog = false },
            onConfirm = {
               editWorkoutViewModel.changeName(it.toString())
                showNameDialog = false   }
                      )
                          }


    if(showReview)  {
        ExercisesReview (listOfIndexedInterval = workoutState.intervalList   , onDismiss ={  showReview= false   } ,
            cardClicked = {
          //  scrollTo = it
            showReview= false
            }
        )

                     }

 Scaffold(modifier = Modifier.fillMaxSize() ,
           topBar ={
               TopAppBarEditWorkOuts(   nameW = workoutState.nameW ,
                   duration = totalDuration ,
                   intervalsNumber =  intervalsNumber  ,
                   setsNumber=  setsNumber ,
                   onReviewClick ={   showReview = true   },
                   textClicked = {  showNameDialog = true  },
                   onReset ={
                       editWorkoutViewModel.undoChange()

                   } ,
                   onBackClick = {
      navController.popBackStack(route = Screens.SavedWorkOutScreen.route ,inclusive = false)
                   },

               )


                   }    ) {
           paddingValues  ->
           Box( modifier = Modifier
               .padding(paddingValues)
               .fillMaxSize()){

               Column(modifier = Modifier.fillMaxSize()) {
 
                   LazyColumn(
                       modifier = Modifier
                           .weight(1f),
                      verticalArrangement = Arrangement.spacedBy(3.dp),
                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 7.dp),
                       state = listState
                   ) {

                       itemsIndexed(
                           workoutState.intervalList

                       ) {

       index, interval ->  key ( interval.id  ) {

                               IntervalModifier(
                                   intervalToModify = interval,

                                   moveUp = {
                                       editWorkoutViewModel.swapIntervalUp(index)
                                   },
                                   moveDown = {
                                       editWorkoutViewModel.swapIntervalDown(index)
                                   },
                                   deleteInterval = {
                                       editWorkoutViewModel. removeInterval( index)
                                                    },
                                   addInterval = {
          val defaultIntervalIndexed=IntervalsInfoIndexed(intervalType=IntervalsType.WORK_OUT,intervalDuration=10000,intervalName ="")

                    editWorkoutViewModel.addInterval(index=index+1,intervalsInfoI=defaultIntervalIndexed)

                                                 },
                                   newInterval = {
                                       mInterval ->
                                   editWorkoutViewModel.updateInterval(index=index, intervalsInfoI = mInterval)
                                   }
                               )
                           }


                       }
   item {
       Row(modifier = Modifier
           .fillMaxWidth()
           .align(Alignment.End), horizontalArrangement = Arrangement.Center) {
                           Text(text = "Ends Here ", fontSize = 35.sp)
                       } }



                   }

               }



  FloatingActionButton(
  modifier = Modifier
      .align(Alignment.BottomEnd)
      .size(80.dp)
      .padding(5.dp),
      onClick = { editWorkoutViewModel.updateWorkOut( )   },
        containerColor = AppColors.mBlueL,
         elevation =FloatingActionButtonDefaults.elevation(defaultElevation=3.dp,pressedElevation=25.dp)

                       ) {
                         Icon(   imageVector = Icons.Filled.Save, contentDescription = "Add Interval"   )    }


                   }




       }

   }






