package my.destinyStudio.firetimer.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import my.destinyStudio.firetimer.data.ExercisesViewModel
import my.destinyStudio.firetimer.screens.editworkout.EditWorkoutViewModel
import my.destinyStudio.firetimer.screens.editworkout.SavedWorkOutEditScreen
import my.destinyStudio.firetimer.screens.savedworkouts.SavedWorkOutCards
import my.destinyStudio.firetimer.screens.settingScreen.SettingScreen
import my.destinyStudio.firetimer.screens.starscreen.StartScreen
import my.destinyStudio.firetimer.screens.timerscreen.TimerAViewModel
import my.destinyStudio.firetimer.screens.timerscreen.TimerBScreen
import my.destinyStudio.firetimer.screens.timerscreen.TimerBViewModel
import my.destinyStudio.firetimer.screens.timerscreen.TimerScreen


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun NavigationOperation(

                        ){

     val exercisesViewModel: ExercisesViewModel = hiltViewModel()
    val timerBViewModel : TimerBViewModel = hiltViewModel()
//    val timerBViewModel : TimerBViewModel =hiltViewModel()
//    val timerAViewModel: TimerAViewModel = hiltViewModel()
//    val editWorkoutViewModel:EditWorkoutViewModel = hiltViewModel()

    val navController = rememberNavController( )

    NavHost(navController = navController,
        startDestination =  Screens.StartScreen.route, enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = EaseInBounce
                )
            ) + slideIntoContainer(
                animationSpec = tween(500, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        } ){

        composable(route =Screens.StartScreen.route ){
           // val exercisesViewModel:ExercisesViewModel = hiltViewModel()
//            val timerBViewModel : TimerBViewModel =hiltViewModel()
        StartScreen(   navController = navController,  exercisesViewModel = exercisesViewModel,   timerBViewModel = timerBViewModel   )
        }


        //TimerB
        composable(
            route = Screens.TimerBScreen.route,
            enterTransition = {
    fadeIn(  animationSpec = tween(  durationMillis = 500, easing = LinearEasing ) ) + slideIntoContainer( animationSpec = tween(500, easing = EaseIn), towards = AnimatedContentTransitionScope.SlideDirection.Start   )
            },
            exitTransition = {
  fadeOut( animationSpec = tween(  durationMillis =  500, easing = LinearEasing  )    ) + slideOutOfContainer(  animationSpec = tween(500, easing = EaseOut),    towards = AnimatedContentTransitionScope.SlideDirection.End  )
            }


        ){
//            val timerBViewModel : TimerBViewModel =hiltViewModel()
            TimerBScreen(navController,timerBViewModel  )

        }

//timerA
        val timerScreen =Screens.TimerScreen.route
        composable( "$timerScreen/{id}", arguments = listOf(navArgument("id"){   type= NavType.StringType }  ) ,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        500, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        500, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(500, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ){

            backStackEntry ->
            val timerAViewModel : TimerAViewModel =hiltViewModel()
            backStackEntry.arguments?.getString("id").let {

                 TimerScreen(navController, timerAViewModel =  timerAViewModel, identifier = it.toString() )
                Log.d("ExeNav", it.toString())


            }
        }

        composable(
            route = Screens.IntervalsScreen.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        500, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        500, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(500, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ){

        }
        //Saved Workout
        composable(
            route = Screens.SavedWorkOutScreen.route,
            enterTransition = {
   fadeIn( animationSpec = tween( 500, easing = LinearEasing )  ) + slideIntoContainer(   animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start )
            },
            exitTransition = {
  fadeOut( animationSpec = tween( 500, easing = LinearEasing )  ) + slideOutOfContainer(  animationSpec = tween(500, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ){
            //val exercisesViewModel: ExercisesViewModel =hiltViewModel()

            SavedWorkOutCards(  exercisesViewModel =  exercisesViewModel, navController = navController  )

        }
        val editScreen =Screens.SavedWorkOutEditScreen.route
        composable(   "$editScreen/{id}", arguments = listOf(navArgument("id" ){ type= NavType.StringType  }
           ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        500, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        100, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(100, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
            ) {

    backStackEntry ->

            val   editWorkoutViewModel:EditWorkoutViewModel = hiltViewModel()

      backStackEntry.arguments?.getString("id").let {
            SavedWorkOutEditScreen(  navController = navController, identifier=it.toString(),  editWorkoutViewModel = editWorkoutViewModel  )
            }



        }


        composable(  route = Screens.InfoScreen.route,
            enterTransition = {
                fadeIn( animationSpec = tween( 500, easing = LinearEasing  ) ) + slideIntoContainer(    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
  fadeOut(  animationSpec = tween(  500, easing = LinearEasing   ) ) + slideOutOfContainer( animationSpec = tween(500, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ){


        }
        composable(
            route = Screens.SettingScreen.route,
            enterTransition = {
    fadeIn(   animationSpec = tween( 500, easing = LinearEasing  ) )+slideIntoContainer(   animationSpec = tween(500, easing = EaseIn), towards = AnimatedContentTransitionScope.SlideDirection.Start)
            },
            exitTransition = {
     fadeOut(animationSpec = tween( 500, easing = LinearEasing  )  ) + slideOutOfContainer( animationSpec = tween(500, easing = EaseOut),  towards = AnimatedContentTransitionScope.SlideDirection.End)
            }
        ){
           SettingScreen(navController)

        }

}
}

