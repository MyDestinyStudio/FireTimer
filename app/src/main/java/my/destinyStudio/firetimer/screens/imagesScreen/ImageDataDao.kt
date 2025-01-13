package my.destinyStudio.firetimer.screens.imagesScreen

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

//@Dao
//interface ImageDataDao {
//    @Insert
//      fun insertImage(imageData: ImageData)
//    @Query("DELETE from images")
//    fun clear()
//    @Query("SELECT * FROM images")
//    fun getAllImages(): Flow<List<ImageData>>
//}
//
//
//class ImageRepository @Inject constructor(
//    private val imageDetailDao: ImageDataDao
//) {
//    fun addImage(imageData: ImageData)=imageDetailDao.insertImage(imageData)
//
//
//  fun clearData()=imageDetailDao.clear()
//
//    fun getAllImages ():Flow<List<ImageData>> =  imageDetailDao.getAllImages().flowOn(
//        Dispatchers.IO).conflate()
//}