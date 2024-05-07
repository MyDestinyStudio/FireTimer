package my.destinyStudio.firetimer.utils

import android.util.Log
import my.destinyStudio.firetimer.components.IntervalsType
import my.destinyStudio.firetimer.data.ExpTA
import my.destinyStudio.firetimer.data.IntervalsInfo
import my.destinyStudio.firetimer.data.IntervalsInfoIndexed
import my.destinyStudio.firetimer.data.WorkOutDetail

fun   edToNormal(intervalsInfo: IntervalsInfo): IntervalsInfoIndexed {
    return IntervalsInfoIndexed(
        intervalType = intervalsInfo.intervalType,
        intervalDuration = intervalsInfo.intervalDuration,
        intervalName = intervalsInfo.intervalName
    )
}
fun  indexedToNormal(intervalsInfoIndexed: IntervalsInfoIndexed): IntervalsInfo {
    return IntervalsInfo(
        intervalType = intervalsInfoIndexed.intervalType,
        intervalDuration = intervalsInfoIndexed.intervalDuration,
        intervalName = intervalsInfoIndexed.intervalName
    )
}
fun  toIndexed(intervalsInfo: List<IntervalsInfo>  ): MutableList <IntervalsInfoIndexed>  {

    return intervalsInfo .map { info ->
        IntervalsInfoIndexed(
            intervalType = info.intervalType,
            intervalDuration = info.intervalDuration,
            intervalName = info.intervalName
        )
    }.toMutableList()
}
fun  toIndexedA( intervalsInfo: List<IntervalsInfo>     ): MutableList <IntervalsInfoIndexed>     {

    val list =  intervalsInfo .map { info ->
            IntervalsInfoIndexed(
                intervalType = info.intervalType,
                intervalDuration = info.intervalDuration,
                intervalName = info.intervalName
            )
        } .toMutableList()

    return list
}

fun  toNormal( intervalsInfoIndexed :List<IntervalsInfoIndexed> ): List<IntervalsInfo> {
    return intervalsInfoIndexed .map { indexedInfo ->
        IntervalsInfo(
            intervalType = indexedInfo.intervalType,
            intervalDuration = indexedInfo.intervalDuration,
            intervalName = indexedInfo.intervalName
        )
    }
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
fun workOutListBuilder(name:String="name",list : List<IntervalsInfo> = ExpTA):WorkOutDetail{
    return WorkOutDetail(workOutName = name, workOutPrimaryDetail = list  )

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
        Log.d("A",result.toString())
    }


  result.add(  IntervalsInfo(intervalType = IntervalsType.REST_BTW_SETS, intervalName ="" , intervalDuration = restBtSets)  )

    repeat(  setsC) {
        result2.addAll(result)
        Log.d("A",result2.toString())
    }

    result2.add(index = 0,IntervalsInfo(intervalType = IntervalsType.WARM_UP, intervalName ="" , intervalDuration = warmUpD))
    result2.removeAt(index = result2.lastIndex)
    result2.add(IntervalsInfo(intervalType = IntervalsType.COOL_DOWN, intervalName ="" , intervalDuration = coolDownD))

    return result2

}

fun workoutGenerator(name:String ,list: MutableList <IntervalsInfo>):WorkOutDetail{


    return WorkOutDetail(workOutName = name, workOutPrimaryDetail = list)

}
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}
