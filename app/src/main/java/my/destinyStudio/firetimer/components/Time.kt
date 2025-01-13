package my.destinyStudio.firetimer.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.destinyStudio.firetimer.ui.theme.dimens


@Preview(backgroundColor = 0xFFCCC2DC, showBackground = true )
@Composable
fun TimePickerCard(modifier : Modifier = Modifier,
                   secondsInPut :Int =10,
                   minutesInPut: Int=0,
                   color: Color= Color.Black,
                   minusColor :Color= Color.White,
                   plusColor: Color= Color.White,
                   borderColor: Color= Color.Red,

                   durationValue : (Long) -> Unit ={},
)   {
    val secondsRange = 0..60
    val minutesRange = 0..180

    val minutes = remember { mutableIntStateOf(minutesInPut) }
    val seconds = remember { mutableIntStateOf(secondsInPut) }
    var intervalDuration =seconds.intValue + minutes.intValue*60
    val duration = intervalDuration*1000L


    durationValue(duration)
    Card(modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
                 ),
        border = BorderStroke(MaterialTheme.dimens.pickerCardBorderTickness, borderColor)
    ) {
        Row(  modifier = Modifier.padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {

            IconButton(onClick = {
                intervalDuration -= 5
                seconds.intValue=intervalDuration%60
                minutes.intValue= intervalDuration/60
                if (seconds.intValue >secondsRange.last)seconds.intValue=60
                if (seconds.intValue < secondsRange.first)seconds.intValue=0
            }) {
                Icon(imageVector =Icons.Filled.Remove, contentDescription = "", tint= minusColor)

            }


                OutlinedTextField(
                     modifier = Modifier.padding(horizontal = 2.dp)
                         .height(MaterialTheme.dimens.pickerCardSize)
                         .  width(MaterialTheme.dimens.pickerCardSize) ,

                    value = minutes.intValue.toString(),
                    onValueChange = { value ->
                        minutes.intValue = value.toIntOrNull() ?: 0

                        if (minutes.intValue >minutesRange.last)minutes.intValue=90
                        if (minutes.intValue < minutesRange.first)minutes.intValue=0
                        if(minutes.intValue<10) minutes.intValue= minutes.intValue.toString().format("%002d", minutes.intValue).toInt()
                    },
               textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.dimens.pickerCardSFontSize .sp,
                        color = color,
                         ),
                    singleLine = true,

                    maxLines = 1,

                    label={ Text(text = "Min", color = color) },

                    colors = OutlinedTextFieldDefaults.  colors(
                        focusedTextColor = color,
                        unfocusedTextColor = color,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor  ),

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                )

                Spacer(modifier = Modifier.width(2.dp))
                Text(text = ":", fontSize = 25.sp ,color=color)
                Spacer(modifier = Modifier.width(2.dp))


                OutlinedTextField(
                    modifier = Modifier.padding(horizontal = 2.dp)
                         .height(MaterialTheme.dimens.pickerCardSize)
                        .  width(MaterialTheme.dimens.pickerCardSize)

                              ,
                    value = seconds.intValue.toString(),
                    onValueChange = { value ->
                        seconds.intValue = value.toIntOrNull() ?: 0
                        if (seconds.intValue >secondsRange.last)seconds.intValue=60
                        if (seconds.intValue < secondsRange.first)seconds.intValue=0
                        if(seconds.intValue<10) seconds.intValue= seconds.intValue.toString().format("%002d", seconds.intValue).toInt()

                    },
                    singleLine = true,
                    maxLines = 1,
                    label={ Text(text = "Sec" , color = color) },
                    colors = OutlinedTextFieldDefaults.
                    colors(
                        focusedTextColor = color,
                        unfocusedTextColor = color,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,


                        ),
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.dimens.pickerCardSFontSize.sp ,
                        color = color),

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )



            IconButton(onClick = {
                intervalDuration += 5
                seconds.intValue=intervalDuration%60
                minutes.intValue= intervalDuration/60
                if (seconds.intValue >secondsRange.last)seconds.intValue=60
                if (seconds.intValue < secondsRange.first)seconds.intValue=0
            }) {
                Icon(imageVector = Icons.Default.Add , contentDescription = "", tint= plusColor)

            }




        }
    }
}




