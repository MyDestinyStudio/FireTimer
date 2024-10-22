package my.destinyStudio.firetimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "workouts_table")
data class  WorkOutDetail(
    @PrimaryKey
    val id : UUID = UUID.randomUUID(),
    @ColumnInfo(name = "workout_name" )
    val workOutName:String="" ,

    @ColumnInfo(name = "workout_intervals" )
    val workOutPrimaryDetail :List<IntervalsInfo>


)

data class WorkoutUiState(
    var idW: String? = null,
    var nameW: String? = null,
    var intervalList: MutableList <IntervalsInfoIndexed> = mutableListOf()
)

val initial = WorkOutDetail(workOutName ="New", workOutPrimaryDetail = listOf() )




   val wdTest = WorkOutDetail(id =  UUID.fromString( "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454") , workOutName ="Test", workOutPrimaryDetail = ExpTA)
val wdTestC = WorkOutDetail(   workOutName ="Test", workOutPrimaryDetail = ExpTUri)