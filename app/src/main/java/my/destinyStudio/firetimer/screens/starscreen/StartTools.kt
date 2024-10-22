package my.destinyStudio.firetimer.screens.starscreen


import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import my.destinyStudio.firetimer.data.ExercisesViewModel
import my.destinyStudio.firetimer.navigation.Screens
import my.destinyStudio.firetimer.ui.theme.AppColors
import my.destinyStudio.firetimer.ui.theme.dimens
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun StartNavBar(navController: NavController =NavController(context = LocalContext.current),
                exercisesViewModel: ExercisesViewModel = hiltViewModel(),
                listIcon:() -> Unit ={},
                calendarIcon:() -> Unit ={},
                homeIcon:() -> Unit ={},
                infoIcon:() -> Unit ={},
                settingIcon:() -> Unit ={},


                ){
  val navBackStackEntry by navController.currentBackStackEntryAsState()

     val exercisesNumber by exercisesViewModel.workoutList.collectAsState()

    var isList by remember {
        mutableStateOf(false)
    }
    var isCalendar by remember {
        mutableStateOf(false)
    }
    var isHome by remember {
        mutableStateOf(false)
    }
    var isInfo by remember {
        mutableStateOf(false)
    }
    var isSetting by remember {
        mutableStateOf(false)
    }


    when(navBackStackEntry?.destination?.route ){

        Screens.CalenderScreen.route-> {
            isCalendar=true
            isInfo=false
            isHome=false
            isSetting=false
            isList=false

        }
        Screens.StartScreen.route-> {
            isCalendar=false
            isInfo=false
            isHome=true
            isSetting=false
            isList=false

        }
        Screens.InfoScreen.route-> {
            isCalendar=false
            isInfo=true
            isHome=false
            isSetting=false
            isList=false

        }
        Screens.SettingScreen.route-> {
            isCalendar=false
            isInfo=false
            isHome=false
            isSetting=true
            isList=false

        }
        Screens.SavedWorkOutScreen.route-> {
            isCalendar=false
            isInfo=false
            isHome=false
            isSetting=false
            isList=true

        }

    }

    Surface(  modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        color = Color.Transparent


        )
       {
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor =Color.Blue
            //AppColors.mBlueL ,

        ) {

            NavigationBarItem(
                icon = {
                    BadgedBox(badge = {
                        if (exercisesNumber.isNotEmpty()) Badge { Text(text = "${exercisesNumber.size}") }
                    }) {
                        Icon(
                            imageVector = if (isList) Icons.Filled.FormatListNumbered
                            else Icons.Outlined.FormatListNumbered, contentDescription = ""
                        )
                    }
                },
                label = { Text("List") },
                selected = isList,

                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.White,
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = AppColors.themeFlamingo,
                ),
                onClick = { listIcon() }
            )

            NavigationBarItem(
                icon = {
                    BadgedBox(badge = {
                        Badge()
                    }) {

                        Icon(
                            imageVector = if (isCalendar) Icons.Filled.CalendarMonth
                            else Icons.Outlined.CalendarMonth, contentDescription = ""
                        )
                    }
                },
                label = { Text("Calendar") },
                selected = isCalendar,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.White,
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor =  AppColors.themeFlamingo

                ),
                onClick = { calendarIcon() }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isHome) Icons.Filled.Home
                        else Icons.Outlined.Home, contentDescription = ""
                    )
                },
                label = { Text("Home") },
                selected = isHome,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.White,
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor =  AppColors.themeFlamingo
                ),
                onClick = { homeIcon() }
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isInfo) Icons.Rounded.Image
                        else Icons.Outlined.Image, contentDescription = ""
                    )
                },
                label = { Text("Info") },
                selected = isInfo,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.White,
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor =  AppColors.themeFlamingo


                ),
                onClick = { infoIcon() }
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isSetting) Icons.Filled.Settings
                        else Icons.Outlined.Settings, contentDescription = ""
                    )
                },
                label = { Text("Setting") },
                selected = isSetting,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.White,
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor =  AppColors.themeFlamingo


                ),
                onClick = { settingIcon() }
            )


        }
    }



    }

// data class BottomNavigationItem(
//     val title:String,
//     val selectedIcon:ImageVector,
//     val unSelectedIcon:ImageVector,
//     val hasCount : Boolean,
//     val badgeCount:Int?
//
// )

//val  navigationList= listOf(
//    BottomNavigationItem(title ="List" , selectedIcon =Icons.Filled.FormatListNumbered ,
//    unSelectedIcon =Icons.Outlined.FormatListNumbered , hasCount = true,
//    badgeCount =12 ),
//    BottomNavigationItem(title ="Home" , selectedIcon =Icons.Filled.Home ,
//        unSelectedIcon =Icons.Outlined.Home , hasCount = true,
//        badgeCount =12 ),
//    BottomNavigationItem(title ="Info" , selectedIcon =Icons.Filled.CalendarMonth,
//        unSelectedIcon =Icons.Outlined.CalendarMonth, hasCount = true,
//        badgeCount =12 ),
//    BottomNavigationItem(title ="Setting" , selectedIcon =Icons.Filled.Settings ,
//        unSelectedIcon =Icons.Outlined.Settings , hasCount = true,
//        badgeCount =12 ),
//
//)


@Composable
@Preview
fun DoubleDeckFab(
    onSave:() ->Unit={},
    onPlay:() ->Unit={}

                  ){
    var offsetX by rememberSaveable {  mutableFloatStateOf(0f) }
    var offsetY by rememberSaveable { mutableFloatStateOf(0f) }

   Column( modifier = Modifier
       .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) } // Apply offset
       .pointerInput(Unit) {
           detectDragGestures { change, dragAmount ->
               change.consume() // Consume the drag event
               offsetX += dragAmount.x
               offsetY += dragAmount.y
           }
       },
       horizontalAlignment = Alignment.CenterHorizontally){
       FloatingActionButton(modifier = Modifier.size( MaterialTheme.dimens.floatingActionButton/2 ),
               shape = CircleShape,
           containerColor =Color.Blue,
        //   AppColors.mBlueL ,

           onClick = { onSave() }

       ){
           Icon(modifier = Modifier ,imageVector =
           Icons.Rounded.Download, contentDescription ="", tint = Color.White)
       }

     Spacer(
         Modifier
             .height(10.dp)
             .width(70.dp) )
        FloatingActionButton(modifier = Modifier.size(MaterialTheme.dimens.floatingActionButton ),
          shape = CircleShape,
            containerColor = AppColors.mOrange ,
     onClick = { onPlay()    }

        ){
            Icon( modifier = Modifier.fillMaxSize() ,imageVector =
            Icons.Rounded.PlayArrow, contentDescription ="", tint = Color.White)
        }
    }
}




