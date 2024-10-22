package my.destinyStudio.firetimer.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import my.destinyStudio.firetimer.data.WorkOutDetail

import javax.inject.Inject



class WorkoutsRepository @Inject constructor(
    private val workOutDetailDao: WorkOutDetailDao
) {
     fun addW(workOutDetail: WorkOutDetail)= workOutDetailDao.insert(workOutDetail)

     fun updateW(workOutDetail: WorkOutDetail)=workOutDetailDao.update(workOutDetail)

    fun getWorkoutById(id: String ): Flow< WorkOutDetail> = workOutDetailDao.getWorkoutById (id).flowOn(Dispatchers.IO).conflate()

    fun deleteW(workOutDetail: WorkOutDetail)=workOutDetailDao.deleteWorkout(workOutDetail)

     fun deleteAllW()=workOutDetailDao.deleteAll()

    fun deleteByID(id: String )=workOutDetailDao.deleteId(id)

    fun getAll ():Flow<MutableList<WorkOutDetail>> =  workOutDetailDao.getWorkouts().flowOn(Dispatchers.IO).conflate()
}

