package my.destinyStudio.firetimer.screens.settingScreen

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import my.destinyStudio.firetimer.R
import my.destinyStudio.firetimer.screens.editworkout.IntervalModifier
import my.destinyStudio.firetimer.screens.savedworkouts.TopAppBarA
import my.destinyStudio.firetimer.ui.theme.AppColors


@Composable
//@Preview
fun SettingScreen(navController: NavController
                      = NavController(context = LocalContext.current)
) {
 //   val settings by viewModel.settings.collectAsState()
    val configuration = LocalConfiguration.current
    BackHandler {
       navController.popBackStack()
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
            GeneralOptionsUI()
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
            .padding(10.dp),
   colors = CardDefaults.cardColors(containerColor = Color.Red),

    ) {
        Row (
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Check Your Profile",
                 //   fontFamily = Poppins,
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

//                Button(
//                    modifier = Modifier.padding(top = 10.dp),
//                    onClick = {},
//                    contentPadding = PaddingValues(horizontal = 30.dp),
//
//                 //   shape = Shapes.medium
//                ) {
//                    Text(
//                        text = "View",
//                      //  fontFamily = Poppins,
//                      //  color = SecondaryColor,
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
            }
            Image(modifier = Modifier .clip(CircleShape),
                painter = painterResource(id = R.drawable.keffie),
                contentDescription = "",

            )
        }
    }
}


@Composable
fun GeneralOptionsUI() {
    Column(
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "General",

            color = Color.Blue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        GeneralSettingItem(
            icon = Icons.Rounded.Notifications,
            mainText = "Notifications",
            subText = "Customize notifications",
            function = { LanguagePicker(modifier = Modifier.padding(vertical =7.dp,horizontal = 10.dp)) }

        )

        GeneralSettingItem(
            icon = Icons.Rounded.Timer,
            mainText = "Timer",
            subText = "Customize it more to fit your usage",
            function = { DefaultIntervalPicker(modifier = Modifier.padding(vertical =7.dp,horizontal = 10.dp)) }

        )

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
            fontSize = 14.sp,
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

//@Preview
@Composable
fun  DefaultIntervalPicker(modifier: Modifier=Modifier){

//    Card(
//        modifier=modifier,
//        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
//        colors = CardDefaults.cardColors(containerColor = AppColors.mOrange)
//    ) {
        Column(modifier = modifier
            //.padding(vertical = 3.dp, horizontal = 5.dp)
        ) {
            Text(text = "Default Interval", fontSize = 20.sp, color = Color.White)
            IntervalModifier( elevation = 35 , showColumn = false)
        }
  //  }
}
//@Preview

@Composable
fun  LanguagePicker(modifier: Modifier=Modifier){

    var expanded by rememberSaveable { mutableStateOf(false) }
    var flagLanguage by remember   { mutableStateOf(FlagLanguage(flag = R.drawable.unitedstates, language = "English", label = "En")) }
    Card(
         modifier=modifier ,
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
         colors = CardDefaults.cardColors(containerColor = AppColors.mOrange)
     ) {

    Column (modifier = modifier.fillMaxWidth()
         .padding(vertical = 3.dp, horizontal = 5.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LanguageCard(modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp), flag = flagLanguage.flag, label = flagLanguage.label){expanded = !expanded}
        AnimatedVisibility (visible = expanded ){
            Column (modifier = Modifier.padding(horizontal = 5.dp), verticalArrangement = Arrangement.spacedBy(5.dp)){
            listOfLanguage.forEach {
                LanguageCard(modifier = Modifier.fillMaxWidth(), flag = it.flag, label = it.language){flagLanguage=it
                expanded=!expanded}

            }
        }
        }

       }
    }
}

data class FlagLanguage(val flag:Int, val language: String, val label: String )


val listOfLanguage = listOf(
    FlagLanguage( flag = R.drawable.unitedstates, language = "English", label = "En"),

    FlagLanguage( flag = R.drawable.france,       language = "French",  label = "Fr"),

    FlagLanguage( flag = R.drawable.arabic,       language = "Arabic",  label = "Ar"),

    FlagLanguage( flag = R.drawable.germany ,     language = "German",  label = "De"),

    FlagLanguage( flag = R.drawable.spain,        language = "Spanish", label = "Sp")
)

@Preview
@Composable
fun  LanguageCard(modifier: Modifier=Modifier,flag:Int= R.drawable.unitedstates,label :String="English",onClick : ( )->Unit= {} ){

    Card(
        modifier=modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.mOrange)
    ) {
    Row(modifier = modifier.clickable { onClick() }, verticalAlignment = Alignment.CenterVertically

    ) {
        Image(modifier = Modifier.size(50.dp).padding(5.dp), painter = painterResource( flag), contentDescription = "Flag")
        Text(modifier = Modifier.padding(horizontal = 5.dp), text = label, color = Color.White)
    }
       }
}



@Composable
fun GeneralSettingItem(icon: ImageVector, mainText: String,
                       subText: String,
                       function: @Composable () -> Unit={}

) {
    var expanded by rememberSaveable   { mutableStateOf( false) }

    val rotation by animateFloatAsState(
        targetValue = if (expanded) -180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    Card(
        onClick = {   expanded =!expanded },
        colors = CardDefaults.cardColors(containerColor = Color.Red),
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),

        ) {
        Column(modifier = Modifier.padding(vertical = 3.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
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
                Icon(modifier = Modifier
                    .rotate(rotation)
                    .size(48.dp),
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = "",
                    tint = Color.White,

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