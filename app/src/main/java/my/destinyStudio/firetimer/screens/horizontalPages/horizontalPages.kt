package my.destinyStudio.firetimer.screens.horizontalPages

//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavController
//import my.destinyStudio.firetimer.data.ExercisesViewModel
//import my.destinyStudio.firetimer.screens.calendarScreen.CalenderScreen
//import my.destinyStudio.firetimer.screens.imagesScreen.ImageDataViewmodel
//import my.destinyStudio.firetimer.screens.imagesScreen.ImagesScreen
//import my.destinyStudio.firetimer.screens.savedworkouts.SavedWorkOutCards
//import my.destinyStudio.firetimer.screens.settingScreen.SettingScreen
//import my.destinyStudio.firetimer.screens.settingScreen.SettingsViewModel
//import my.destinyStudio.firetimer.screens.starscreen.StartScreen
//import my.destinyStudio.firetimer.screens.timerscreen.TimerBViewModel

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun HorizontalPagers(navController:NavController
//                     ,exercisesViewModel: ExercisesViewModel,
//                     imageViewModel:ImageDataViewmodel,
//                     timerBViewModel: TimerBViewModel,
//                     settingViewmodel: SettingsViewModel,
//
//
//
//                     ){
//
//    val index by settingViewmodel.pageIndex.collectAsState()
//
//
//    val pages = remember {
//        // Correct: remember the list of composable functions
//        listOf<@Composable () -> Unit>(
//            { SavedWorkOutCards(exercisesViewModel, navController,settingViewmodel) },
//            { CalenderScreen(settingViewmodel) },
//            { StartScreen(navController, exercisesViewModel, timerBViewModel,settingViewmodel) },
//            { ImagesScreen(navController,imageViewModel, exercisesViewModel,settingViewmodel) },
//            { SettingScreen(  settingViewmodel) }
//        )
//    }
//    val pagerState = rememberPagerState(initialPage = index) {
//        pages.size
//    }
//
////    BackHandler   {
////
////      //  settingViewmodel.backHandler()
////
////      Log.d("Screens","BackHandler Pager ")
////
////    }
//
//    LaunchedEffect(index) {
//        pagerState.animateScrollToPage(index)
//    }
//
//
//    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress,index) {
//        if(!pagerState.isScrollInProgress) {
//            settingViewmodel.indexPage(pagerState.currentPage)
//        }
//    }
//
//Surface(Modifier.fillMaxSize()) {
//
//  HorizontalPager(
//            state = pagerState,
//            modifier = Modifier
//                .fillMaxSize()
//
//
//        ) {
//
//                page ->
//            pages[page]()
//        }
//
//
//}
//}