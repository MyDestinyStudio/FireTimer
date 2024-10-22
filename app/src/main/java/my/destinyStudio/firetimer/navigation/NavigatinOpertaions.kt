package my.destinyStudio.firetimer.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import my.destinyStudio.firetimer.data.ExercisesViewModel
import my.destinyStudio.firetimer.screens.calendarScreen.CalenderScreen
import my.destinyStudio.firetimer.screens.editworkout.EditWorkoutViewModel
import my.destinyStudio.firetimer.screens.editworkout.SavedWorkOutEditScreen
import my.destinyStudio.firetimer.screens.imagesScreen.ImageDataViewmodel
import my.destinyStudio.firetimer.screens.imagesScreen.ImagesScreen
import my.destinyStudio.firetimer.screens.savedworkouts.SavedWorkOutCards
import my.destinyStudio.firetimer.screens.settingScreen.SettingScreen
import my.destinyStudio.firetimer.screens.starscreen.StartNavBar
import my.destinyStudio.firetimer.screens.starscreen.StartScreen
import my.destinyStudio.firetimer.screens.timerscreen.TimerAViewModel
import my.destinyStudio.firetimer.screens.timerscreen.TimerBScreen
import my.destinyStudio.firetimer.screens.timerscreen.TimerBViewModel
import my.destinyStudio.firetimer.screens.timerscreen.TimerScreen


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun NavigationOperation( ){

     val exercisesViewModel: ExercisesViewModel = hiltViewModel()
    val timerBViewModel : TimerBViewModel =  hiltViewModel()
    val imageViewModel : ImageDataViewmodel =  hiltViewModel()
     val navController = rememberNavController( )
    var showBottomBar by remember { mutableStateOf(true) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar  = when(navBackStackEntry?.destination?.route) {
        Screens.StartScreen.route-> true
        Screens.SavedWorkOutScreen.route-> true
        Screens.CalenderScreen.route-> true
        Screens.InfoScreen.route-> true
        Screens.SettingScreen.route -> true
        else -> false

    }

    Scaffold(
        bottomBar = {

              if(showBottomBar)  {
                  StartNavBar(navController=navController,
                listIcon = {  navController.navigate(route = Screens.SavedWorkOutScreen.route    )    },
                calendarIcon = { navController.navigate(Screens.CalenderScreen.route)  },
                homeIcon = { navController.navigate(Screens.StartScreen.route)  },
                infoIcon = { navController.navigate(Screens.InfoScreen.route)  },
                settingIcon = { navController.navigate(Screens.SettingScreen.route)  }
            )}
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "main_nav_graph",
            modifier = Modifier.padding(innerPadding)
        ) {
            navigation(startDestination = Screens.StartScreen.route,route = "main_nav_graph"   )
            {

    composable(route = Screens.StartScreen.route){StartScreen( navController = navController,exercisesViewModel=exercisesViewModel,timerBViewModel=timerBViewModel)}
    composable(route = Screens.SavedWorkOutScreen.route) { SavedWorkOutCards( exercisesViewModel = exercisesViewModel, navController = navController )  }
    composable(route = Screens.CalenderScreen.route){   CalenderScreen() }
    composable(route = Screens.InfoScreen.route){   ImagesScreen(imageDataViewmodel = imageViewModel, exercisesViewModel = exercisesViewModel)}
    composable(route = Screens.SettingScreen.route){  SettingScreen(navController)  }
            }


  navigation(  route = "timer_nav_graph", startDestination = Screens.TimerBScreen.route  ) {
    composable( route = Screens.TimerBScreen.route   ){
        TimerBScreen(navController = navController, timerBViewModel = timerBViewModel )  }


      }


//timerA
      navigation(  route = "timerA_nav_graph", startDestination = Screens.TimerScreen.route  ) {

        val timerScreen =Screens.TimerScreen.route
        composable( "$timerScreen/{id}",arguments=listOf(navArgument("id"){type= NavType.StringType})){
   backStackEntry ->
            val timerAViewModel : TimerAViewModel = hiltViewModel()
            backStackEntry.arguments?.getString("id").let {
              TimerScreen(navController, timerAViewModel =  timerAViewModel, identifier = it.toString() )

   }
        }
            }

 navigation(  route = "edit_nav_graph",  startDestination = Screens.SavedWorkOutEditScreen.route
        ) {

        val editScreen =Screens.SavedWorkOutEditScreen.route
        composable(   "$editScreen/{id}", arguments = listOf(navArgument("id" ){ type= NavType.StringType  } )   ) {

    backStackEntry ->  val   editWorkoutViewModel:EditWorkoutViewModel = hiltViewModel()

      backStackEntry.arguments?.getString("id").let {
            SavedWorkOutEditScreen(  navController = navController, identifier=it.toString(),
                editWorkoutViewModel = editWorkoutViewModel , imageDataViewmodel = imageViewModel )
            }
    }



    }

    }
    }



}


