package my.destinyStudio.firetimer.data




import androidx.compose.runtime.Immutable
import java.util.UUID


data class  IntervalsInfo(val intervalType:String ,
                          val  intervalDuration:Long  ,
                          val  intervalName:String
)



@Immutable
data class IntervalsInfoIndexed(
    val intervalType: String,
    val intervalDuration: Long,
    val intervalName: String,
    val id: UUID = UUID.randomUUID()
)