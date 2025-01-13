

package my.destinyStudio.firetimer.navigation



import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import my.destinyStudio.firetimer.data.ExercisesViewModel
import my.destinyStudio.firetimer.screens.SplashScreen
import my.destinyStudio.firetimer.screens.calendarScreen.CalenderScreen
import my.destinyStudio.firetimer.screens.editworkout.EditWorkoutViewModel
import my.destinyStudio.firetimer.screens.editworkout.SavedWorkOutEditScreen
import my.destinyStudio.firetimer.screens.imagesScreen.ImageDataViewmodel
import my.destinyStudio.firetimer.screens.imagesScreen.ImagesScreen
import my.destinyStudio.firetimer.screens.savedworkouts.SavedWorkOutCards
import my.destinyStudio.firetimer.screens.settingScreen.SettingScreen
import my.destinyStudio.firetimer.screens.settingScreen.SettingsViewModel
import my.destinyStudio.firetimer.screens.starscreen.StartNavBar
import my.destinyStudio.firetimer.screens.starscreen.StartScreen
import my.destinyStudio.firetimer.screens.timerscreen.TimerAViewModel
import my.destinyStudio.firetimer.screens.timerscreen.TimerBScreen
import my.destinyStudio.firetimer.screens.timerscreen.TimerBViewModel
import my.destinyStudio.firetimer.screens.timerscreen.TimerScreen


@Serializable
object StartScreen
@Serializable
object TimerBScreen
@Serializable
data class TimerAScreen(val id:String?=null )
@Serializable
object ScheduleScreen
@Serializable
object SavedWorkOutScreen
@Serializable
data class  EditScreen(val id:String?=null )
 @Serializable
 object ImageScreen
 @Serializable
 object SettingScreen
@Serializable
object SplashScreen


@Serializable
object TimerBGraph
@Serializable
object TimerGraph

@Serializable
object SplashGraph

@Serializable
object EditGraph
@Serializable
object MainGraph



@SuppressLint("RestrictedApi", "StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun NavigationOperation(


){

    val settingViewmodel: SettingsViewModel =  hiltViewModel()
    val exercisesViewModel: ExercisesViewModel = hiltViewModel()
    val timerBViewModel : TimerBViewModel =  hiltViewModel()
    val imageViewModel : ImageDataViewmodel =  hiltViewModel()


     val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

   var showBottomBar by remember { mutableStateOf(true) }

showBottomBar = navBackStackEntry?.destination?.hierarchy?.any { it.hasRoute (MainGraph::class) } == true

Log.d("Nav","${navController.currentBackStackEntry}")
    Log.d("Nav","Stack ${ navController.currentBackStack.value }")

    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
  AnimatedVisibility(
      visible = showBottomBar,
      enter = slideInVertically( animationSpec = spring(stiffness = 1700f),initialOffsetY = { it }),
      exit = slideOutVertically( animationSpec = spring(stiffness = 1700f),targetOffsetY = { it })
    ) {
      StartNavBar(
         settingViewModel = settingViewmodel, exercisesViewModel = exercisesViewModel,
          onListClick ={
              navController.navigate(SavedWorkOutScreen  ){
              popUpTo(navController.graph.findStartDestination().id) { saveState = true  }
              launchSingleTop = true
              restoreState=true
                       }

                       } ,
          onCalendarClick ={navController.navigate(ScheduleScreen  ){
              popUpTo(navController.graph.findStartDestination().id) { saveState = true  }
              launchSingleTop = true
              restoreState=true
          }  } ,
          onHomeClick ={navController.navigate(StartScreen   ){
              popUpTo(navController.graph.findStartDestination().id) { saveState = true  }
              launchSingleTop = true
              restoreState=true
          }} ,
          onImageClick ={navController.navigate(ImageScreen ){
              popUpTo(navController.graph.findStartDestination().id) { saveState = true  }
              launchSingleTop = true
              restoreState=true
          }} ,
          onSettingClick ={navController.navigate(SettingScreen   ){
              popUpTo(navController.graph.findStartDestination().id) { saveState = true  }
              launchSingleTop = true
              restoreState=true
          }}
      ) }
                     }

    ) {

        innerPadding ->

  NavHost(
            navController = navController,
            startDestination = SplashGraph ,
            modifier = Modifier.padding(innerPadding)
        ) {

 navigation <SplashGraph>( startDestination = SplashScreen )  {

                composable <SplashScreen> {
     SplashScreen(  navController = navController, settingViewModel = settingViewmodel )
                }

            }


  navigation<MainGraph> (startDestination = StartScreen  )  {





 composable< StartScreen >(
     enterTransition = {
     slideIntoContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Start) },
     exitTransition = {slideOutOfContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Down)},
     popEnterTransition ={slideIntoContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Left )} ,
     popExitTransition = {slideOutOfContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Right)}



 ) {
     StartScreen(   navController,   exercisesViewModel,
                 timerBViewModel,   settingViewmodel )
        }


  composable<SavedWorkOutScreen>(
      enterTransition = {
          slideIntoContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Left) },
      exitTransition = {slideOutOfContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Right)},
      popEnterTransition ={slideIntoContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Left )} ,
      popExitTransition = {slideOutOfContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Right)}



  ) {

      SavedWorkOutCards(   navController ,  exercisesViewModel, settingViewmodel )
  }

  composable <ScheduleScreen>(
      enterTransition = {
          slideIntoContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Left) },
      exitTransition = {slideOutOfContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Right)},
      popEnterTransition ={slideIntoContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Left )} ,
      popExitTransition = {slideOutOfContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Right)}



  ) {
      CalenderScreen( navController,settingViewmodel)
  }

   composable <ImageScreen>(
       enterTransition = {
           slideIntoContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Right) },
       exitTransition = {slideOutOfContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Left)},
       popEnterTransition ={slideIntoContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Left )} ,
       popExitTransition = {slideOutOfContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Right)}



   ){

       ImagesScreen(  navController, imageViewModel,  exercisesViewModel,   settingViewmodel )

   }

   composable<SettingScreen>( enterTransition = {
       slideIntoContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Right) },
       exitTransition = {slideOutOfContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Left)},
       popEnterTransition ={slideIntoContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Left )} ,
       popExitTransition = {slideOutOfContainer(animationSpec = tween(300), towards = AnimatedContentTransitionScope.SlideDirection.Right)}){
       SettingScreen( navController, settingViewmodel  )
   }


    }




    navigation<TimerBGraph> (  startDestination =  TimerBScreen    ) {
        composable <TimerBScreen> {
            TimerBScreen(
                navController = navController,
                timerBViewModel = timerBViewModel,
                settingViewModel = settingViewmodel
            )
        }


    }



    navigation <TimerGraph>( startDestination  = TimerAScreen("")  ) {

      composable <TimerAScreen > {
            val args = it.toRoute<TimerAScreen>()
            val timerAViewModel: TimerAViewModel = hiltViewModel()

            TimerScreen(
                identifier = args.id,
                navController = navController,
                timerAViewModel = timerAViewModel,
                settingViewModel = settingViewmodel,
                        )

        }
    }

    navigation<EditGraph>(
       startDestination = EditScreen("")
    ) {


        composable<EditScreen>{
            val args = it.toRoute< EditScreen>()

            val editWorkoutViewModel: EditWorkoutViewModel = hiltViewModel()
              SavedWorkOutEditScreen(
                    navController = navController,
                    identifier = args.id,
                    editWorkoutViewModel = editWorkoutViewModel,
                    imageDataViewmodel = imageViewModel,
                    settingViewModel = settingViewmodel
                )
                      }

      }




               }
          }
    }



