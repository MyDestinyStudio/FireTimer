package my.destinyStudio.firetimer.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun RepeatNumber(label:String="Work/Rest number",initialC:Int=1,  repeatValue : (Int) -> Unit={} ){
    val range = 1..100
    var cycleNumbers  by remember { (mutableIntStateOf(initialC)) }

    Card(modifier = Modifier
        .padding(2.dp)
        .fillMaxSize() , colors = CardDefaults.cardColors(
            containerColor = Color.Transparent

        )
        ,
        shape = RoundedCornerShape(size = 15.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)

    ) {

  Row(modifier = Modifier.padding(3.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {


            Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Box( Modifier.fillMaxWidth() ,contentAlignment = Alignment.Center){
                    Text(text = label,  fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.headlineMedium ,
                        fontSize = 20.sp)
                }
                Row(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {    cycleNumbers = if (cycleNumbers > 1) cycleNumbers - 1 else 1 }) {
                        Icon(imageVector =  Icons.Default.Remove, contentDescription ="" )
                    }

                    Text(
                        text = "$cycleNumbers", modifier = Modifier
                            .padding(start = 9.dp, end = 9.dp)
                            .align(Alignment.CenterVertically)
                    )
                    IconButton(onClick = {   if (cycleNumbers < range.last) cycleNumbers += 1}) {
                        Icon(imageVector =  Icons.Default.Add, contentDescription = "")
                    }


                }
            }


        }
    }
    repeatValue(cycleNumbers)
}