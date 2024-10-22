package my.destinyStudio.firetimer.data

import java.util.UUID


data class  IntervalsInfo(
            val intervalType:String ,
            val  intervalDuration:Long  ,
            val  intervalName:String,
            val uri : String? = null
)




data class IntervalsInfoIndexed(
    val id: UUID = UUID.randomUUID(),
    val intervalType: String,
    val intervalDuration: Long,
    val intervalName: String,
    var uri: String? =  null
)