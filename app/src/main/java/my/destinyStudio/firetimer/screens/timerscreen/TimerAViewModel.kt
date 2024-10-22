package my.destinyStudio.firetimer.screens.timerscreen

import android.annotation.SuppressLint
import android.app.Application
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import my.destinyStudio.firetimer.R
import my.destinyStudio.firetimer.data.WorkOutDetail
import my.destinyStudio.firetimer.repository.WorkoutsRepository
import javax.inject.Inject


@HiltViewModel

class TimerAViewModel  @Inject constructor(private val repository: WorkoutsRepository,application: Application) :
    AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext

    private val  soundPool = SoundPool.Builder()
        .setMaxStreams(1)  // Maximum concurrent streams
        .setAudioAttributes(  AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        )
        .build()



    private var _playingWorkout = MutableStateFlow<WorkOutDetail?>  (null)
    val playingWorkout: StateFlow<WorkOutDetail?> = _playingWorkout .asStateFlow()

    private  var timeTotalList by mutableLongStateOf(  0L    )


    // Timer State
    private var _isTimerRunning = MutableStateFlow(true)
    var isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private val _pausedTime = MutableStateFlow<Long?>(null)
    private val pausedTime = _pausedTime.asStateFlow()
//    private var _exerciseName =  MutableStateFlow  ("")
//    val  exerciseName: StateFlow<String> =_exerciseName.asStateFlow()

    private var soundPlayed = false


    private val tic = 20L

    private var value =  MutableStateFlow (0f)

    val outer: StateFlow<Float> = value .asStateFlow()

    private var value1 =  MutableStateFlow (0f)

    val inner : StateFlow<Float> = value1.asStateFlow()



    private  var i =   MutableStateFlow (0)
    val index : StateFlow<Int> =  i.asStateFlow()

    private var currentTime =  MutableStateFlow  (0L)
    val curTe: StateFlow<Long> = currentTime.asStateFlow()




    private var goingForward =  MutableStateFlow  (true)
    val isN: StateFlow<Boolean> =goingForward.asStateFlow()




    private  var exercisesTotalTime by   mutableLongStateOf(timeTotalList)


    private var _showAlert=  MutableStateFlow  (false)
    val showAlert: StateFlow<Boolean> =_showAlert.asStateFlow()

//    private var _isLoaded=  MutableStateFlow  (false)
//    val  isLoaded: StateFlow<Boolean> =_isLoaded.asStateFlow()
//
//    fun isLoadedFalse()= viewModelScope.launch  (Dispatchers.IO) {
//
//        _isLoaded.value = true
//
//
//        Log.d("T","isPause" )
//    }








    private var startTime by   mutableLongStateOf(0L)


    private val _elapsedTime = MutableStateFlow(0L)


    private var elapsedTime  by   mutableLongStateOf(0L)


    private var job: Job? = null

//////////////////////////////////////////

    /////////////////////////////////////////////




    fun startTimer()  = viewModelScope.launch  (Dispatchers.IO){
        if (job?.isActive == true) return@launch // Already running

        _isTimerRunning.value = true
        if (pausedTime.value != null) {
            // Resume from paused time
            val pausedDuration = System.currentTimeMillis() - pausedTime.value!!
            startTime += pausedDuration
            _pausedTime.value = null // Reset pausedTime
        } else {

            startTime = System.currentTimeMillis()
        }

     playingWorkout.value?.let {

         workout ->
            timeTotalList = workout.workOutPrimaryDetail.sumOf { it.intervalDuration }
         Log.d("A","Start")
         job = viewModelScope.launch {

             while (isTimerRunning.value) {
                 try {


                     elapsedTime = System.currentTimeMillis() - startTime
                     currentTime.value = workout.workOutPrimaryDetail[i.value].intervalDuration - elapsedTime
  exercisesTotalTime= workout.workOutPrimaryDetail.subList(i.value,playingWorkout.value!!.workOutPrimaryDetail.size).sumOf { it.intervalDuration } - elapsedTime

                     value.value = currentTime.value / workout.workOutPrimaryDetail[i.value].intervalDuration.toFloat()
                     value1.value = exercisesTotalTime / timeTotalList.toFloat()


                     if (currentTime.value in 0..3000L && !soundPlayed  ) {
                         playSound()
                         soundPlayed = true // Set the flag to true after playing the sound
                     }

//                     _exerciseName.value= if(workout.workOutPrimaryDetail[i.value].intervalName!=""){workout.workOutPrimaryDetail[i.value].intervalName}
//                     else{workout.workOutPrimaryDetail[i.value].intervalType}


                     if ( currentTime.value == 0L || currentTime.value < 0L ) {
                         soundPlayed=false
                         i.value++
                         startTime = System.currentTimeMillis()
                         _elapsedTime.value = System.currentTimeMillis() - startTime
                         currentTime.value = workout.workOutPrimaryDetail[i.value].intervalDuration
                     }



                     delay(tic)
                 } catch (e: IndexOutOfBoundsException) {
                     _isTimerRunning.value = false
                     _showAlert.value =true
                 }

             }
         }

        }




    }
//



    /////
    private fun playSound() =  viewModelScope.launch {

        val soundId = soundPool.load (context, R.raw.treescondscountdown, 1)
        soundPool.setOnLoadCompleteListener { _, _, _ ->
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
        }

    }

    ///

    fun pauseTimer()= viewModelScope.launch  (Dispatchers.IO) {

        _pausedTime.value = System.currentTimeMillis()
        _isTimerRunning.value = false
        job?.cancel()


        Log.d("T","isPause" )
    }

    fun resetTimer()= viewModelScope.launch  (Dispatchers.IO)  {
        job?.cancel()
        _playingWorkout.value =  null
        _isTimerRunning.value=false
        _showAlert.value=false
        _pausedTime.value =  null
        value.value=0f
        value1.value=0f
        i.value=0



    }
    fun nextInterval()= viewModelScope.launch  (Dispatchers.IO)  {
        job?.cancel()
        i.value++
        goingForward.value=true
        startTimer()


    }
    fun previousInterval()= viewModelScope.launch  (Dispatchers.IO)  {
        job?.cancel()
        i.value--
        goingForward.value=false
        startTimer()

    }


//    fun resumeTimer() = viewModelScope.launch  (Dispatchers.IO) {
//        _isTimerRunning.value = true
//    }



    fun retrieveW(w:String)=viewModelScope.launch (Dispatchers.IO){

       repository.getWorkoutById(w).collect { workoutDetail -> _playingWorkout.value=workoutDetail  }

    }





}