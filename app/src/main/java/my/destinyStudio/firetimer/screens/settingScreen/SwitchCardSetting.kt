package my.destinyStudio.firetimer.screens.settingScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview

 fun SwitchCardSetting(
    modifier: Modifier=Modifier,
    upperText:String= "Pause on incoming calls",
    downText:String= "The timer will be paused automatically on incoming calls",
    iconChecked : ImageVector = Icons.Filled.Check,
    iconUnChecked : ImageVector =  Icons.Filled.Close,
    isChecked :Boolean= true,
    showPermission:Boolean= false,
    isEnabled:Boolean= true,
    description:String ="Click  to grant  Permissions",
    booleanValue : (Boolean) -> Unit={},
    onPermissionClick  : ( ) -> Unit={}
){

    var checked by rememberSaveable{ mutableStateOf(isChecked ) }

    booleanValue(checked)

     Card (
         modifier = modifier,
         colors = CardDefaults.cardColors(containerColor =  Color.Red)

     ){

  Row(modifier = Modifier.padding(horizontal = 5.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
             Column(Modifier.weight(1f)) {
                 Text(text = upperText, color = Color.White , fontWeight = FontWeight.Bold)
                 Text(text = downText, color = Color.White, fontWeight = FontWeight.Light)

             }

             Switch(
                 modifier = Modifier.semantics { contentDescription = "Demo" },
                 checked = checked,
                 onCheckedChange = { checked = it },
                 colors = SwitchDefaults.colors(
                     checkedThumbColor = Color.Red,
                     checkedTrackColor = Color.White,
                     uncheckedThumbColor = Color.Gray,
                     uncheckedTrackColor = Color.White,


                 ), enabled = isEnabled,
                 thumbContent = {
                     if (checked) {
                         // Icon isn't focusable, no need for content description
                         Icon(
                             imageVector = iconChecked,
                             contentDescription = null,
                             modifier = Modifier.size(SwitchDefaults.IconSize),
                             tint = Color.White
                         )
                     }else{
                         Icon(
                             imageVector = iconUnChecked,
                             contentDescription = null,
                             modifier = Modifier.size(SwitchDefaults.IconSize),
                             tint = Color.White
                         )
                     }
                 }
             )
         }

         AnimatedVisibility (  modifier = Modifier.align(Alignment.CenterHorizontally), visible = showPermission) {
             Row(modifier = Modifier.align(Alignment.End).padding(horizontal = 5.dp, vertical = 7.dp).clickable { onPermissionClick() },
                 verticalAlignment = Alignment.CenterVertically) {
                 Text(text = description, color = Color.White, fontWeight = FontWeight.Light)
                 Icon(imageVector = Icons.Rounded.FilterList, contentDescription = "kk", tint = Color.White)
             }
         }
     }
 }

