package my.destinyStudio.firetimer.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import my.destinyStudio.firetimer.components.IntervalsType
import my.destinyStudio.firetimer.data.IntervalsInfo
import my.destinyStudio.firetimer.data.IntervalsInfoIndexed
import my.destinyStudio.firetimer.data.WorkOutDetail
import java.io.File
import java.sql.Date
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
fun generateImageWorkoutA(uris: List<File>, sets:Int =1, wD:Long, rD:Long  ) : WorkOutDetail {


    val imagedPart = mutableListOf <IntervalsInfo>()

    val imagedPart2 = mutableListOf <IntervalsInfo>()

    uris.forEach { file ->
        imagedPart.add(
            IntervalsInfo(
                intervalType = IntervalsType.WORK_OUT,
                intervalName = "",
                intervalDuration = wD,
                uri = file.absolutePath
            )
        )
        imagedPart.add(
            IntervalsInfo(
                intervalType = IntervalsType.REST,
                intervalName = "",
                intervalDuration = rD
            )
        )
    }
    imagedPart.removeLast()
    imagedPart.add(  IntervalsInfo(intervalType = IntervalsType.REST_BTW_SETS, intervalName ="" , intervalDuration = 30000)  )




    repeat( sets) {

        imagedPart2.addAll(imagedPart)

    }

    imagedPart2. add(index = 0,
        IntervalsInfo(intervalType = IntervalsType.WARM_UP, intervalName ="" , intervalDuration =30000)
    )
    imagedPart2.removeAt(index = imagedPart2.lastIndex)
    imagedPart2.add(IntervalsInfo(intervalType = IntervalsType.COOL_DOWN, intervalName ="" , intervalDuration = 12000))
    imagedPart2.forEach {
        Log.d("A",it.toString())
    }
    return WorkOutDetail(workOutName = Date.from(Instant.now()).toString() , workOutPrimaryDetail = imagedPart2)


}
//}
fun  toIndexed( intervalsInfo: List<IntervalsInfo?>   ): MutableList <IntervalsInfoIndexed>     {

    val list =  intervalsInfo .map { info ->
            IntervalsInfoIndexed(
                intervalType = info?.intervalType ?: IntervalsType.OTHER,
                intervalDuration = info?.intervalDuration ?: 0L,
                intervalName = info?.intervalName ?: "",
                uri = info?.uri
            )
        } .toMutableList()

  //  Log.d("F","toIndexed")
    return list
}

fun  toNormal( intervalsInfoIndexed :List<IntervalsInfoIndexed> ): List<IntervalsInfo> {

   val list = intervalsInfoIndexed .map { indexedInfo ->
       IntervalsInfo(
           intervalType = indexedInfo.intervalType,
           intervalDuration = indexedInfo.intervalDuration,
           intervalName = indexedInfo.intervalName,
           uri = indexedInfo.uri
       )
   }
    Log.d("F","toNormal")
    return list

}
fun formatToMMSSMillis(milliseconds: Long): String {
    val minutes = milliseconds / (1000 * 60)
    val seconds = (milliseconds % (1000 * 60)) / 1000
    val millis = milliseconds % 1000
    return String.format("%02d:%02d.%1d", minutes, seconds, millis)
}

fun formatToMMSS(milliseconds: Long): String {
    val minutes = milliseconds / (1000 * 60)
    val seconds = (milliseconds % (1000 * 60)) / 1000
    return String.format("%02d:%02d", minutes, seconds)
}

fun  workOutListBuilder(  warmUpD:Long , workOutD:Long  , restD:Long , workRestC:Int ,
                    restBtSets:Long , setsC :Int , coolDownD:Long
                ):MutableList<IntervalsInfo>{
    val result = mutableListOf <IntervalsInfo>()
    val result2 = mutableListOf <IntervalsInfo>()

    val wrIntervals = mutableListOf (
        IntervalsInfo(intervalType = IntervalsType.WORK_OUT, intervalName ="" , intervalDuration = workOutD),
        IntervalsInfo(intervalType = IntervalsType.REST, intervalName ="" , intervalDuration = restD)
      )
    repeat(workRestC) {
        result.addAll(wrIntervals)

    }

    result.removeLast()
  result.add(  IntervalsInfo(intervalType = IntervalsType.REST_BTW_SETS, intervalName ="" , intervalDuration = restBtSets)  )

    repeat(  setsC) {
        result2.addAll(result)

    }

    result2.add(index = 0,IntervalsInfo(intervalType = IntervalsType.WARM_UP, intervalName ="" , intervalDuration = warmUpD))
    result2.removeAt(index = result2.lastIndex)
    result2.add(IntervalsInfo(intervalType = IntervalsType.COOL_DOWN, intervalName ="" , intervalDuration = coolDownD))

   // Log.d("wListBuilder",result2.toString())
    return result2


}


fun workoutGenerator(list: MutableList <IntervalsInfo>):WorkOutDetail{
    val name= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Date.from(Instant.now()).toString()
    } else {
        "ff"
    }


    return WorkOutDetail(workOutName = name, workOutPrimaryDetail = list)

}
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}
