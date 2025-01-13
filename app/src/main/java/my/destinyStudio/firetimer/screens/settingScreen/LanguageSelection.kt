package my.destinyStudio.firetimer.screens.settingScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import my.destinyStudio.firetimer.R
import my.destinyStudio.firetimer.ui.theme.AppColors


data class FlagLanguage(val flag:Int, val language: String, val label: String )


val listOfLanguage = listOf(
    FlagLanguage( flag = R.drawable.unitedstates,  language = "English",    label = "en"),

    FlagLanguage( flag = R.drawable.france,        language = "French",     label = "fr"),

    FlagLanguage( flag = R.drawable.arabic,        language = "Arabic",     label = "ar"),

    FlagLanguage( flag = R.drawable.germany ,      language = "German",     label = "de"),

    FlagLanguage( flag = R.drawable.spain,         language = "Spanish",    label = "es"),
    FlagLanguage( flag = R.drawable.grecce,        language = "Greek",      label = "el"),
    FlagLanguage( flag = R.drawable.poland,        language = "Polish",     label = "pl"),
    FlagLanguage( flag = R.drawable.russia,        language = "Russian",    label = "ru"),
    FlagLanguage( flag = R.drawable.italy,         language = "Italian",    label = "it"),
    FlagLanguage( flag = R.drawable.portugal,      language = "Portuguese", label = "pt"),
    FlagLanguage( flag = R.drawable.dutsh,         language = "Dutch",      label = "nl")

)

 @Preview
 @Composable
fun  LanguagePicker(
     modifier: Modifier = Modifier,
                    language: String="en",
                    selectedLanguage: (String) -> Unit={}
){


    var expanded by rememberSaveable  { mutableStateOf(false) }

   Column(
        modifier=modifier ,

    ) {


  LanguageCard(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
                arrangementHorizontal = Arrangement.Center,
                language = when(language){
                    "en" ->  FlagLanguage( flag = R.drawable.unitedstates, language = "English", label = "en")
                    "fr" ->  FlagLanguage( flag = R.drawable.france,       language = "French",  label = "fr")
                    "ar" ->  FlagLanguage( flag = R.drawable.arabic,       language = "Arabic",  label = "ar")
                    "de" ->  FlagLanguage( flag = R.drawable.germany ,     language = "German",  label = "de")
                    "es" ->  FlagLanguage( flag = R.drawable.spain,        language = "Spanish", label = "es")
                    "el" ->    FlagLanguage( flag = R.drawable.grecce,     language = "Greek", label = "el")
                    "pl" ->   FlagLanguage( flag = R.drawable.poland,      language = "Polish", label = "pl")
                    "ru" -> FlagLanguage( flag = R.drawable.russia,        language = "Russian", label = "ru")
                    "it" ->   FlagLanguage( flag = R.drawable.italy,       language = "Italian", label = "it")
                    "pt" ->   FlagLanguage( flag = R.drawable.portugal,    language = "Portuguese", label = "pt")
                    "nl" ->  FlagLanguage( flag = R.drawable.dutsh,        language = "Dutch", label = "nl")
                    else  -> FlagLanguage( flag = R.drawable.unitedstates, language = "English", label = "en")


                }){expanded = !expanded}

            AnimatedVisibility (modifier = Modifier.height(220.dp), visible = expanded  ){



                LazyVerticalGrid  (modifier = Modifier.padding(horizontal = 5.dp) ,
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ){
                    items ( listOfLanguage){item ->

                        LanguageCard(modifier = Modifier .height(30.dp) , language = item){
                            //flagLanguage=it
                            selectedLanguage(item.label)

                        } }

                }
            }


    }
}




//@Preview
@Composable
fun  LanguageCard(modifier: Modifier = Modifier,
                  language: FlagLanguage= FlagLanguage( flag = R.drawable.unitedstates, language = "English", label = "en"),
                  arrangementHorizontal: Arrangement.Horizontal = Arrangement.Start
                  , onClick : ( )->Unit= {} ){
    Card(
        modifier=modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.mOrange)
    ) {
        Row(
            modifier = modifier.fillMaxWidth().clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =  arrangementHorizontal

        ) {
            Image(modifier = Modifier
                .size(50.dp)
                .padding(5.dp), painter = painterResource(language.flag), contentDescription = "Flag")
            Text(modifier = Modifier.padding(horizontal = 5.dp), text = language.language, color = Color.White)
        }

    }
}
