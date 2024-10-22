package my.destinyStudio.firetimer.di

import androidx.room.Database
import androidx.room.RoomDatabase
import my.destinyStudio.firetimer.screens.imagesScreen.ImageData
import my.destinyStudio.firetimer.screens.imagesScreen.ImageDataDao

@Database(entities = [ImageData::class], version = 1)

abstract class ImageDataBase  : RoomDatabase() {
    abstract fun imageDao(): ImageDataDao


}