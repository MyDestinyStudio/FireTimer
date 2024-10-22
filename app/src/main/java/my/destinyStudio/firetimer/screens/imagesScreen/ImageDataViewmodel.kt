package my.destinyStudio.firetimer.screens.imagesScreen

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import my.destinyStudio.firetimer.components.IntervalsType
import my.destinyStudio.firetimer.data.IntervalsInfo
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class ImageDataViewmodel   : ViewModel() {

    private val _internalStorageImageFiles = MutableStateFlow<List<File>>(emptyList())
    val internalStorageImageFiles = _internalStorageImageFiles.asStateFlow()

    private var _selectedImageFile = MutableStateFlow<List<File> >(emptyList())
    val selectedImageFile  = _selectedImageFile.asStateFlow()

    private val _internalStorageImageFilesUris = MutableStateFlow<List<String>>(emptyList())
    val internalStorageImageFilesUris = _internalStorageImageFilesUris.asStateFlow()


    fun copyImagesToInternalStorage(context:  Context, uris: List<Uri>) = viewModelScope.launch(Dispatchers.IO){

        val internalStoragePath = context.filesDir.absolutePath
        val imagesFolder = "$internalStoragePath/FireTimer/Pics" // Create images folder

        File(imagesFolder).mkdirs() // Create folder if it doesn't exist

        uris.forEach { uri ->
            val filename = getFilenameFromUri(context, uri)
            val targetFile = File("$imagesFolder/$filename")

            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val outputStream = FileOutputStream(targetFile)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()

            } catch (e: Exception) {
                // Handle exceptions here
            }
        }
          _internalStorageImageFiles.value = getInternalStorageImages(context)
//          internalStorageImageFiles.value.forEach {
//              Log.d("copy",   it.absolutePath    )
//          }

    }


    fun loadImages (context: Context) = viewModelScope.launch(Dispatchers.IO){
         _internalStorageImageFiles.value = getInternalStorageImages(context)
        _internalStorageImageFiles.value.forEach {

            file: File ->
            Log.d("IV", "File path: ${file.absolutePath}")

        }

    }
       private fun getFilenameFromUri(context: Context, uri: Uri): String {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    return cursor.getString(nameIndex)
                }
            }
        }
        return uri.lastPathSegment ?: ""
    }
    fun removeFromSelectedList ( file:File) = viewModelScope.launch(Dispatchers.IO){
   _selectedImageFile.value= _selectedImageFile.value.toMutableList().also {  it.remove( file) }.toList()
    }

    fun removeImage(context: Context ,index: Int ) = viewModelScope.launch (Dispatchers.IO){


        if(_internalStorageImageFiles.value[index] in _selectedImageFile.value ){  _selectedImageFile.value= _selectedImageFile.value.toMutableList().also {  it.remove(_internalStorageImageFiles.value[index]) }.toList()  }

        deleteFileFromStorage(context = context, fileName = _internalStorageImageFiles.value[index].name )

    }
     private fun deleteFileFromStorage(context: Context, fileName: String) {


            val file = File(context.filesDir.absolutePath + File.separator + "FireTimer" + File.separator + "Pics", fileName)
            Log.d("IV", "File path: ${file.absolutePath}")
            Log.d("IV", "File name: $fileName")

            if (file.exists()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    // For Android Q and above, use ContentResolver
                    try {
                     val contentResolver = context.contentResolver
                   val uri = FileProvider.getUriForFile( context, "${context.packageName}.fileprovider", file  )
                   contentResolver.delete(uri, null, null)
                    Log.d ("IV","delete above 10 ")
                    loadImages(context)
                    }catch(e: Exception) {

                        Log.d ("IV","delete above 10 Catch ")

                    }
                } else {
                    try {
                    file.delete()
                    loadImages(context)
                    Log.d ("IV","delete under 10 ")
                    }catch(e: Exception) {
                        Log.d ("IV","delete under 10 Catch ")
                    }
                }

               // Log.d ("IV","Success")
            } else {
                Log.d ("IV","File none existing")
            }
//        }
//        catch (e: Exception) {
//           Log.d ("IV","catch  ")
//        }
    }
    fun removeImageFromSelected(index: Int) = viewModelScope.launch (Dispatchers.IO){
     _selectedImageFile.value =  _selectedImageFile.value.toMutableList().also {  it.removeAt(index) }.toList()
     }
    fun generateImageWorkout(uris: List<File>,sets:Int =1,wD:Long  ,rD:Long  ) = viewModelScope.launch (Dispatchers.IO){

      //  val result: MutableList<IntervalsInfo>

        val imagedPart = mutableListOf <IntervalsInfo>()
         val imagedPart2 = mutableListOf <IntervalsInfo>()

          uris.forEach {
              file ->
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
        imagedPart.add(  IntervalsInfo(intervalType = IntervalsType.REST_BTW_SETS, intervalName ="" , intervalDuration = 30000)  )

            repeat( sets) {
                imagedPart2.addAll(imagedPart)

            }

            imagedPart2. add(index = 0,
                IntervalsInfo(intervalType = IntervalsType.WARM_UP, intervalName ="" , intervalDuration =30000)
            )
            imagedPart2.removeAt(index = imagedPart.lastIndex)
            imagedPart2.add(IntervalsInfo(intervalType = IntervalsType.COOL_DOWN, intervalName ="" , intervalDuration = 12000))
          imagedPart2.forEach {
              Log.d("A",it.toString())
          }


    }
    private fun getInternalStorageImages(context: Context): List<File> {
        val imagesFolder = File(context.filesDir, "FireTimer/Pics")
        _internalStorageImageFilesUris.value=imagesFolder.listFiles()?.filter { it.isFile }?.map { file ->
            Uri.fromFile(file).toString() // Convert File to URI
        } ?: emptyList()
        return imagesFolder.listFiles()?.filter { it.isFile } ?: emptyList()
    }

    fun selectImage(file: File)= viewModelScope.launch(Dispatchers.IO) {
       val a = _selectedImageFile.value .toMutableList()
        a .add(file)
        _selectedImageFile.value =   a .toList()
    }

    fun renameFileInInternalStorage(context: Context, oldFileName: String, newFileName: String): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            val oldFilePath = context.filesDir.absolutePath + File.separator + "FireTimer" + File.separator + "Pics" + oldFileName
            val newFilePath = context.filesDir.absolutePath + File.separator + "FireTimer" + File.separator + "Pics" + newFileName

            try {
                // 1. Read the file contents
                val inputStream = FileInputStream(oldFilePath)
                val outputStream = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                inputStream.close()
                val fileContents = outputStream.toByteArray()
                outputStream.close()

                // 2. Delete the original file
                val oldFile = File(oldFilePath)
                oldFile.delete()

                // 3. Write the file contents to the new file
                val newFile = File(newFilePath)
                val fileOutputStream = FileOutputStream(newFile)
                fileOutputStream.write(fileContents)
                fileOutputStream.close()

                // Success
                Log.d("FileRename", "File renamed successfully")

            } catch (e: IOException) {
                Log.e("FileRename", "Error renaming file: ${e.message}")
            }
        }
    }


}


