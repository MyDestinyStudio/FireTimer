package my.destinyStudio.firetimer.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import my.destinyStudio.firetimer.data.WorkOutDetail


@Dao
    interface WorkOutDetailDao {

        @Query("SELECT * from workouts_table ")
        fun getWorkouts() : Flow<MutableList <WorkOutDetail>>

        @Query("SELECT * from workouts_table where id =:id")
         fun getWorkoutById(id: String): Flow<WorkOutDetail>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
       fun insert(note: WorkOutDetail)

        @Update(onConflict = OnConflictStrategy.REPLACE)
         fun update(note: WorkOutDetail)

        @Query("DELETE from workouts_table")

         fun deleteAll()

         @Query(" Delete   from workouts_table where id =:id")
        fun deleteId(id: String)

        @Delete
         fun deleteWorkout(note: WorkOutDetail)


    }