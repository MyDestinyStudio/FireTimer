package my.destinyStudio.firetimer.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import my.destinyStudio.firetimer.repository.WorkOutDetailDao
import my.destinyStudio.firetimer.screens.settingScreen.dataStore
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides

    fun provideNotesDao(workoutDetailDataBase: WorkoutDetailDataBase): WorkOutDetailDao
            =   workoutDetailDataBase.workOutDetailDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WorkoutDetailDataBase
            = Room.databaseBuilder(
        context,
        WorkoutDetailDataBase::class.java,
        "workouts_table")
        .fallbackToDestructiveMigration()
        .build()

       @Singleton
        @Provides
        fun provideContext(app: Application): Context {
            return app
        }
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }


//    @Singleton
//    @Provides
//
//    fun provideImagesDao(imageDataBase: ImageDataBase ): ImageDataDao
//            =   imageDataBase.imageDao()
//
//    @Singleton
//    @Provides
//    fun getDatabase(@ApplicationContext context: Context): ImageDataBase  = Room.databaseBuilder(
//                context  ,
//                ImageDataBase::class.java,
//                "image_database"
//            ) .fallbackToDestructiveMigration().build()


        }

