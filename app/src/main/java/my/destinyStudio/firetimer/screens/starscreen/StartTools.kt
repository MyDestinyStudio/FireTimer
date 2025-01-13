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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import my.destinyStudio.firetimer.R
import my.destinyStudio.firetimer.data.ExercisesViewModel
import my.destinyStudio.firetimer.screens.settingScreen.SettingsViewModel
import my.destinyStudio.firetimer.ui.theme.AppColors
import my.destinyStudio.firetimer.ui.theme.dimens
import kotlin.math.roundToInt


@Composable
@Preview
fun StartNavBar(
    navController: NavController= NavController(context =   LocalContext.current),
    exercisesViewModel: ExercisesViewModel = hiltViewModel(),
    settingViewModel: SettingsViewModel = hiltViewModel(),
    onListClick:()-> Unit= {},
    onCalendarClick:()-> Unit= {},
    onHomeClick:()-> Unit= {},
    onImageClick:()-> Unit= {},
    onSettingClick:()-> Unit= {},

    ){


   val selectedItemIndex by settingViewModel.pageIndex.collectAsState()

    val exercisesNumber by exercisesViewModel.workoutList.collectAsState()



    Surface(  modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        color = Color.Transparent
          )
       {
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor =AppColors.verdigris
                     ) {

            NavigationBarItem(
                icon = {
                    BadgedBox(badge = {
                        if (exercisesNumber.isNotEmpty()) Badge { Text(text = "${exercisesNumber.size}") }
                    }) {
                        Icon(
                            imageVector = if (selectedItemIndex == 0) Icons.Filled.FormatListNumbered
                            else Icons.Outlined.FormatListNumbered, contentDescription = ""
                        )
                    }
                },
                label = { Text(stringResource(R.string.list) ) },
                selected = selectedItemIndex == 0,

                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = AppColors.WHITE,
                    selectedIconColor = AppColors.WHITE,
                    selectedTextColor = AppColors.WHITE ,
                    unselectedTextColor =AppColors.WHITE,
                    indicatorColor = AppColors.Tangelo,
                ),
                onClick = {
                   onListClick()
                      settingViewModel.indexPage(0)
                }
            )

            NavigationBarItem(
                icon = {
                    BadgedBox(badge = {
                        Badge()
                    }) {

                        Icon(
                            imageVector = if (selectedItemIndex ==  1) Icons.Filled.CalendarMonth
                            else Icons.Outlined.CalendarMonth, contentDescription = ""
                        )
                    }
                },
                label = { Text(stringResource(R.string.calendar) ) },
                selected = selectedItemIndex ==  1 ,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = AppColors.WHITE,
                    selectedIconColor = AppColors.WHITE,
                    selectedTextColor = AppColors.WHITE ,
                    unselectedTextColor =AppColors.WHITE,
                    indicatorColor = AppColors.Tangelo,

                ),
                onClick = {
                   onCalendarClick()
                 settingViewModel.indexPage(1)
                }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex ==  2) Icons.Filled.Home
                        else Icons.Outlined.Home, contentDescription = ""
                    )
                },
                label = { Text(stringResource(R.string.home) ) },
                selected = selectedItemIndex ==  2,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = AppColors.WHITE,
                    selectedIconColor = AppColors.WHITE,
                    selectedTextColor = AppColors.WHITE ,
                    unselectedTextColor =AppColors.WHITE,
                    indicatorColor = AppColors.Tangelo,
                ),
                onClick = {
                   onHomeClick()
                    settingViewModel.indexPage(2)
                }
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex ==  3) Icons.Rounded.Image
                        else Icons.Outlined.Image, contentDescription = ""
                    )
                },
                label = { Text(stringResource(R.string.info) ) },
                selected =selectedItemIndex == 3,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = AppColors.WHITE,
                    selectedIconColor = AppColors.WHITE,
                    selectedTextColor = AppColors.WHITE ,
                    unselectedTextColor =AppColors.WHITE,
                    indicatorColor = AppColors.Tangelo,


                ),
                onClick = {
                      onImageClick()
                   settingViewModel.indexPage(3)
                }
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex ==  4) Icons.Filled.Settings
                        else Icons.Outlined.Settings, contentDescription = ""
                    )
                },
                label = { Text(stringResource(R.string.setting) ) },
                selected = selectedItemIndex ==  4,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = AppColors.WHITE,
                    selectedIconColor = AppColors.WHITE,
                    selectedTextColor = AppColors.WHITE ,
                    unselectedTextColor =AppColors.WHITE,
                    indicatorColor = AppColors.Tangelo,
                     ),
                onClick = {
                    onSettingClick()
                    settingViewModel.indexPage(4)
                }
            )


        }
    }



    }

// data class BottomNavigationItem(
//     val title:String,
//     val selectedIcon: ImageVector,
//     val unSelectedIcon:ImageVector,
//     val hasCount : Boolean,
//     val badgeCount:Int?
//
// )

//val  navigationList= listOf(
//    BottomNavigationItem(title ="List" , selectedIcon =Icons.Filled.FormatListNumbered ,
//        unSelectedIcon =Icons.Outlined.FormatListNumbered , hasCount = true,
//        badgeCount =12 ),
//
//    BottomNavigationItem(title ="Calendar" , selectedIcon =Icons.Filled.CalendarMonth ,
//        unSelectedIcon =Icons.Outlined.CalendarMonth , hasCount = true,
//        badgeCount =12 ),
//
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
//    )

@Composable
//@Preview
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




