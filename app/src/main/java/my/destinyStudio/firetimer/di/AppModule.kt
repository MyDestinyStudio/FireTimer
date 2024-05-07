package my.destinyStudio.firetimer.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import my.destinyStudio.firetimer.repository.WorkOutDetailDao
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

}