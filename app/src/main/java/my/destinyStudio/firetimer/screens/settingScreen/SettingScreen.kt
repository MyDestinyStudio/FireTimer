package my.destinyStudio.firetimer.screens.settingScreen

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Backup
import androidx.compose.material.icons.rounded.Feedback
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material.icons.rounded.SettingsApplications
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.yariksoffice.lingver.Lingver
import my.destinyStudio.firetimer.R
import my.destinyStudio.firetimer.data.IntervalsInfoIndexed
import my.destinyStudio.firetimer.navigation.ImageScreen
import my.destinyStudio.firetimer.navigation.SettingScreen
import my.destinyStudio.firetimer.screens.savedworkouts.TopAppBarA
import my.destinyStudio.firetimer.ui.theme.AppColors


@Composable
//@Preview
fun SettingScreen(
                    navController    : NavController ,
                    settingViewmodel : SettingsViewModel = hiltViewModel()
                  ) {

    val settings by settingViewmodel.settings.collectAsState()
    val isPermissionsForPhoneCallGranted by settingViewmodel.isPermissionsForPhoneCallGranted.collectAsState()
    val configuration = LocalConfiguration.current
    val context = LocalContext.current



    var showDialog  by  rememberSaveable { mutableStateOf(false) }
    var permissionRequested  by  rememberSaveable { mutableStateOf("") }
    val  dialogPermissionTextProvider  by settingViewmodel.permissionsTextProvider.collectAsState()


    val launcherCallPermission = rememberLauncherForActivityResult(
        contract =  ActivityResultContracts.RequestPermission()
    )
    {  isGranted ->  if(isGranted)settingViewmodel .onOnePermissionResult(  permission =Manifest.permission.READ_PHONE_STATE, isGranted =   isGranted   )
   else if (!isGranted) {
       settingViewmodel.dialogPermissionTextProvider(PhoneCallPermissionTextProvider())
        showDialog  =true
   }
        permissionRequested=Manifest.permission.READ_PHONE_STATE

       Log.i("Settings Screen ", "permission granted $isGranted")
    }

   // val lifecycleOwner = LocalLifecycleOwner.current

//    DisposableEffect(lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_PAUSE) {
//
//
//                Log.d("SettingsScreen","OnPause")
//
//            } else if (event == Lifecycle.Event.ON_RESUME  ) {
//                showDialog=false
//                Log.d("SettingsScreen","OnStart onResume")
//            }
//
//        }
//
//
//        lifecycleOwner.lifecycle.addObserver(observer)
//        onDispose {
//            Log.d("SettingsScreen","OnDispose ")
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }



    BackHandler  {
        settingViewmodel.indexPage(3)
        navController.navigate(ImageScreen, navOptions { popUpTo(SettingScreen ){inclusive=true} } )

        Log.d("Settings Screen","BackHandler Settings")
    }

      if( showDialog ){

     PermissionDialog( permissionTextProvider = dialogPermissionTextProvider,
         isPermanentlyDeclined =  !shouldShowRequestPermissionRationale(    (context as  ComponentActivity) ,permissionRequested  ),
         onGoToAppSettingsClick = {
             (context as Activity).openAppSettings()
             showDialog=false

                                  },
         onDismiss = {showDialog=false}  )
      }


    Scaffold(modifier = Modifier.fillMaxSize()
        , topBar = {
            when {
                configuration.orientation== Configuration.ORIENTATION_LANDSCAPE &&configuration.screenHeightDp<480 -> {

                }
                else -> {
            TopAppBarA (
                title = "Setting"
            )}}

        }
    ) {
        pad  ->

        Column(modifier = Modifier
            .padding(pad)
            .verticalScroll(rememberScrollState())) {

            ProfileCardUI()

            Column(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.general),

                    color = Color.Blue,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )
                GeneralSettingItem(
                    icon = Icons.Rounded.SettingsApplications,
                    mainText = "General",
                    //stringResource(R.string.General),
                    subText = stringResource(R.string.customize_notifications),
                    content = {

   LanguagePicker(modifier = Modifier.background(Color.Transparent) .padding(vertical =7.dp,horizontal = 10.dp),
                            language =settings .language ) {
                            settingViewmodel .updateLanguage(it)
                          Lingver.getInstance().setLocale(context = context, language = it)
                            (context as? ComponentActivity)?.recreate()


                        }




                    }

                )

                GeneralSettingItem(
                    icon = Icons.Rounded.Timer,
                    mainText = stringResource(R.string.timer),
                    subText = stringResource(R.string.customize_it_more_to_fit_your_usage),
                    content = {

                        Spacer(modifier = Modifier .fillMaxWidth()  .height(2.dp) .background(Color.White))

                        SwitchCardSetting(modifier = Modifier.padding(horizontal =  5.dp, vertical = 3.dp), isChecked = settings.callPauseEnabled,
                            showPermission = ! isPermissionsForPhoneCallGranted,
                            isEnabled =  isPermissionsForPhoneCallGranted,
                            onPermissionClick ={

                                launcherCallPermission.launch( Manifest.permission.READ_PHONE_STATE  )
                                //(  context as Activity).openAppSettings()
                            }  ,

                            booleanValue = {
                                Log.d("Settings Screen","  value $it")
                                if(!it) {settingViewmodel.updateCall(false)}
                                else if(it){

                                    if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
                                        settingViewmodel.updateCall(false)


                                        Log.d("Settings Screen","not granted value $it")



                                    }else if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){

                                        settingViewmodel.updateCall(it)

                                        Log.d("Settings Screen","  granted value $it")
                                    }

                                }

                            })

                        Spacer(modifier = Modifier .fillMaxWidth()  .height(2.dp) .background(Color.White))

                        SwitchCardSetting(modifier = Modifier.padding(horizontal =  5.dp, vertical = 3.dp), isChecked = settings.vibrationEnabled,
                            upperText = "Vibrations",
                            downText = "Vibrated in the end of each interval",
                            booleanValue = {
                                settingViewmodel.updateVibration(it)
                            }

                        )
                        Spacer(modifier = Modifier .fillMaxWidth()  .height(2.dp) .background(Color.White))
                        DefaultIntervalPicker(
                            interval = IntervalsInfoIndexed(intervalName = settings.defaultIntervalName,
                            intervalType = settings.defaultIntervalType, intervalDuration = settings.defaultIntervalDuration,
                            uri = settings.defaultIntervalUri),
                            modifier = Modifier.padding(vertical =5.dp,horizontal = 3.dp)
                        ){
                            settingViewmodel.updateInterval(it)
                        }


                        Spacer(modifier = Modifier .fillMaxWidth()  .height(2.dp) .background(Color.White))

                        TicPicker (modifier =  Modifier
                            .padding(horizontal = 3.dp, vertical = 5.dp), currentTic = settings.tic){
                            settingViewmodel.updateTic(it)
                            Log.d("T","S $it" )
                        }
                        Spacer(modifier = Modifier
                            .fillMaxWidth()

                            .height(2.dp)
                            .background(Color.White))

                        DefaultWorkoutEditor(modifier = Modifier.padding(vertical =5.dp,horizontal = 3.dp).fillMaxWidth(),


                            warmUpDurationP = settings.defaultWarmUpDuration /1000,
                            workOutDurationP =settings.defaultWorkDuration /1000 ,
                            restDurationP =settings.defaultRestDuration /1000
                            , setsNumbersP =settings.defaultSets.toInt() ,
                            restBtwSetsDurationP = settings.defaultRestDuration /1000,
                            cycleNumbersP = settings.defaultCycles.toInt(),
                            cooldownDurationP =settings.defaultCoolDownDuration /1000,

                        ) {
                            settingViewmodel.updateDefaultWorkout(it)

                        }

                    }

                )

            }
            SupportOptionsUI()
        }


    }

}




@Composable
fun ProfileCardUI() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(3.dp),
   colors = CardDefaults.cardColors(containerColor = Color.Red),

    ) {
        Row (
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Check Your Profile",

 color =Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = "UI.Stack.YT@gmail.com",
                   // fontFamily = Poppins,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                )

            }
            Image(modifier = Modifier .clip(CircleShape),
                painter = painterResource(id = R.drawable.keffie),
                contentDescription = "",

            )
        }
    }
}






@Composable
fun SupportOptionsUI() {
    Column(
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .padding(top = 5.dp)
    ) {
        Text(
            text = "Support",
            fontSize = 24.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        SupportItem(
            icon = Icons.Rounded.Backup,
            mainText = "Back Up",

            )
        SupportItem(
            icon = Icons.Rounded.Feedback,
            mainText = "Feedback",

            )
        SupportItem(
            icon = Icons.Rounded.PrivacyTip,
            mainText = "Privacy Policy",

            )
        SupportItem(
            icon =Icons.Rounded.Info,
            mainText = "About",
            function = {
                Text(  text = "This App is For Fat people to become skinny i", fontSize = 30.sp, color = Color.White)
            }

        )
    }
}






@Composable
fun GeneralSettingItem(icon: ImageVector, mainText: String,
                       subText: String,
                       content: @Composable (AnimatedVisibilityScope.() -> Unit)={},


) {
    var expanded by rememberSaveable   { mutableStateOf( false) }

    val rotation by animateFloatAsState(
        targetValue = if (expanded) -180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    Card(
      //  onClick = {   expanded =!expanded },
        colors = CardDefaults.cardColors(containerColor = Color.Red),
        modifier = Modifier
            .padding(bottom = 3.dp, top = 3.dp)
            .fillMaxWidth(),

        ) {
      //  Column(modifier = Modifier.padding(vertical = 3.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Row (
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )


                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = mainText,

                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = subText,

                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.offset(y = (-4).dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))
                Icon(modifier = Modifier.clickable { expanded =!expanded  }
                    .rotate(rotation)
                    .size(48.dp),
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = "",
                    tint = Color.White,

                    )

            }

            AnimatedVisibility (modifier = Modifier.background(AppColors.mLightGray), visible = expanded ){
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .height(2.dp)
                    .background(Color.Red))
                Column {content( )}
            }
       // }
    }
}

@Composable
fun SupportItem(icon: ImageVector, mainText: String ,
                function: @Composable () -> Unit={}) {
    var expanded by rememberSaveable   { mutableStateOf( false) }
    val rotation by animateFloatAsState(
        targetValue = if (expanded) -180f else 0f,
        animationSpec = tween(durationMillis = 300), label = ""
    )
    Card(
        onClick = {   expanded =!expanded  },
        colors = CardDefaults.cardColors(containerColor = Color.Red),
        modifier = Modifier
            .padding(bottom = 3.dp)
            .fillMaxWidth(),

    ) {
        Column {
            Row(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                Icon(
                    imageVector = icon,
                    contentDescription = "",

                    modifier = Modifier.size(36.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    text = mainText,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = "",
                    modifier = Modifier
                        .rotate(rotation)
                        .size(30.dp),
                    tint = Color.White
                )

            }

            AnimatedVisibility (visible = expanded ){
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .height(2.dp)
                    .background(Color.White))
                function( )
            }
        }
    }
}