package my.destinyStudio.firetimer.screens.editworkout

 
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import my.destinyStudio.firetimer.data.IntervalsInfoIndexed
import my.destinyStudio.firetimer.navigation.SavedWorkOutScreen
import my.destinyStudio.firetimer.screens.imagesScreen.ImageDataViewmodel
import my.destinyStudio.firetimer.screens.settingScreen.SettingsViewModel
import my.destinyStudio.firetimer.ui.theme.AppColors
import kotlin.math.roundToInt


@Composable

fun SavedWorkOutEditScreen(
    navController: NavController=NavController(context = LocalContext.current),
    identifier:String?,
    editWorkoutViewModel: EditWorkoutViewModel= hiltViewModel(),
    imageDataViewmodel: ImageDataViewmodel= hiltViewModel(),
    settingViewModel: SettingsViewModel = hiltViewModel()
){


    val internalStorageImageFilesUris by imageDataViewmodel.internalStorageImageFilesUris.collectAsState()
    val workoutState              by editWorkoutViewModel.workoutUi.collectAsState()
    val totalDuration             by editWorkoutViewModel.totalDuration.collectAsState()
    val intervalsNumber           by  editWorkoutViewModel.intervalsNumber.collectAsState()
    val setsNumber                by  editWorkoutViewModel.setsNumber.collectAsState()
    val settings                  by settingViewModel.settings.collectAsState()


    val listState = rememberLazyListState()
    val context = LocalContext.current

    var showReview             by rememberSaveable { mutableStateOf(false) }
    var showNameDialog         by rememberSaveable { mutableStateOf(false) }
    var isLoaded               by rememberSaveable { mutableStateOf(true) }
    var offsetX                by rememberSaveable {  mutableFloatStateOf(0f) }
    var offsetY                by rememberSaveable { mutableFloatStateOf(0f) }

    var scrollTo by remember {
        mutableIntStateOf(0)
    }


    BackHandler {
      //  navController.navigate( Screens.SavedWorkOutScreen.route )
                 }

  LaunchedEffect( key1 = identifier, key2 = isLoaded) {
if(isLoaded) {
    identifier?.let { editWorkoutViewModel.getWByIdd(it) }

    imageDataViewmodel.loadImages(context)
    isLoaded = false
    Log.d("F", "Re W")

}
    }


  LaunchedEffect(key1 = scrollTo){

        listState.animateScrollToItem(scrollTo)
      Log.d("F", "Scroll")
    }




    if (showNameDialog) {
        NameChangeAlertDialog(
            initialName = workoutState.nameW,
            onDismiss={showNameDialog=false},
            onConfirm = {
               editWorkoutViewModel.changeName(it.toString())
                showNameDialog = false}
                      )
                       }

   if(showReview)  {
        ExercisesReview (
            listOfIndexedInterval = workoutState.intervalList  ,
            onDismiss ={ showReview= false} ,
            cardClicked = {
           scrollTo = it
            showReview= false
            }
        )

                }

 Scaffold(modifier = Modifier.fillMaxSize() ,
           topBar ={
               TopAppBarEditWorkOuts(   nameW = workoutState.nameW,
                   duration = totalDuration ,
                   intervalsNumber =  intervalsNumber  ,
                   setsNumber=  setsNumber ,
                   onReviewClick ={   showReview = true   },
                   textClicked = {  showNameDialog = true  },
                   onReset ={
                       editWorkoutViewModel.undoChange()
                              } ,
                   onBackClick = {
      navController.navigate(  SavedWorkOutScreen  )
                   },

               )


                   } , floatingActionButton =  {
         FloatingActionButton(
             modifier = Modifier
                 .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                 .pointerInput(Unit) {
                     detectDragGestures { change, dragAmount ->
                         change.consume()
                         offsetX += dragAmount.x
                         offsetY += dragAmount.y
                     }
                 }
                 .size(70.dp)
                ,
             onClick = {
                 editWorkoutViewModel.updateWorkOut()
                 workoutState.intervalList.forEach { ii ->  Log.d("Edit", "Fab $ii") }
                       },
             shape = CircleShape,
             containerColor = AppColors.mOrange ,
             elevation = FloatingActionButtonDefaults.elevation(defaultElevation=3.dp,pressedElevation=25.dp)

         ) {
             Icon(  imageVector = Icons.Rounded.Download , contentDescription = "Add Interval", tint = Color.White   )    }

     }  ) {
           paddingValues  ->

             LazyColumn( modifier = Modifier
                 .fillMaxSize()
                 .padding(paddingValues),
                      verticalArrangement = Arrangement.spacedBy(3.dp),
                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 5.dp),
                       state = listState
                   ) {

                       itemsIndexed(
                           workoutState.intervalList

                       ) {

       index, interval ->  key ( interval.id  ) {

                               IntervalModifier(modifier =   Modifier
                                   .fillMaxWidth()
                                   .height(150.dp),
                                   intervalToModify = interval,
                                   context=context,
                                   list =internalStorageImageFilesUris ,

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


                    editWorkoutViewModel.addInterval(index=index+1,intervalsInfoI=IntervalsInfoIndexed(
                        intervalType =settings .defaultIntervalType ,
                        intervalDuration =settings .defaultIntervalDuration,
                        intervalName =settings .defaultIntervalName,
                        uri = settings.defaultIntervalUri))

                                                 },
                                   newInterval = {
                                       mInterval ->

                                       editWorkoutViewModel.updateInterval(index=index,intervalsInfoI = mInterval)
                                   },

                               )
                           }


                       }




                   }

               }





            }