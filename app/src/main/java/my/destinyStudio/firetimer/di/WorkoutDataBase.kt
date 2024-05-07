package my.destinyStudio.firetimer.di

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import my.destinyStudio.firetimer.data.WorkOutDetail

import my.destinyStudio.firetimer.repository.WorkOutDetailDao
import my.destinyStudio.firetimer.utils.ListConverter
import my.destinyStudio.firetimer.utils.UUIDConverter

@Database(entities = [WorkOutDetail::class], version = 1, exportSchema = false)

@TypeConverters(  UUIDConverter::class,   ListConverter::class
)
abstract class WorkoutDetailDataBase : RoomDatabase() {
    abstract fun workOutDetailDao(): WorkOutDetailDao
}