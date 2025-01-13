package my.destinyStudio.firetimer.screens.timerscreen

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.telephony.TelephonyManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
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
import my.destinyStudio.firetimer.utils.workOutListBuilder
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class TimerBViewModel @Inject constructor(
    @SuppressLint("StaticFieldLeak") private val context: Context) :
    ViewModel( )  {



    private val  soundPool = SoundPool.Builder()
    .setMaxStreams(1)  // Maximum concurrent streams
    .setAudioAttributes(  AudioAttributes.Builder()
    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
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



    private  var i =   MutableStateFlow (0)
    val index : StateFlow<Int> =  i.asStateFlow()

    private var currentTime =  MutableStateFlow  (100000L)
    val curTe: StateFlow<Long> = currentTime.asStateFlow()

    private var goingForward =  MutableStateFlow  (true)
    val isN: StateFlow<Boolean> =goingForward.asStateFlow()

    private var _showAlert=  MutableStateFlow  (false)
    val showAlert: StateFlow<Boolean> =_showAlert.asStateFlow()



    private  var exercisesTotalTime by   mutableLongStateOf(timeTotalList)


    private var startTime by   mutableLongStateOf(0L)


    private var tic = 20L

    private var vibration = true



    private var value =  MutableStateFlow(0f)

    val outer: StateFlow<Float> = value.asStateFlow()

    private var value1 =  MutableStateFlow(0f)

    val inner : StateFlow<Float> = value1.asStateFlow()


    private val _elapsedTime = MutableStateFlow(0L)


    private var elapsedTime  by   mutableLongStateOf(0L)


    private var job: Job? = null
    private var soundPlayed = false



//    private val _callState = MutableLiveData<String>()
//    val callState: LiveData<String> = _callState

    private val phoneCallReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d("TBViewMode", "onReceive in ViewModel is called")

            try {
                if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
                    val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)

                    when (state) {
                        TelephonyManager.EXTRA_STATE_RINGING -> {
                            pauseTimer()
                            Log.d("TBViewMode", "Incoming call from: ")

                        }

                        TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                            pauseTimer()
                            Log.d("TBViewMode", "Call answered")

                        }

                        TelephonyManager.EXTRA_STATE_IDLE -> {
                           startTimer()
                            Log.d("TBViewMode", "Call ended or missed")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("TBViewMode", "Error processing incoming call", e)
            }
        }
    }








    init {
        registerReceiver(context)
    }

    private fun registerReceiver(context: Context) {
        val intentFilter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        context.registerReceiver(phoneCallReceiver, intentFilter)

        Log.d("TBViewMode","Registered")
    }

    private fun unregisterReceiver(context: Context){
        context.unregisterReceiver(phoneCallReceiver)
        Log.d("TBViewMode","UnRegistered")
    }



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
            Log.d("TBViewMode","Start")
            job = viewModelScope.launch {

                while (isTimerRunning.value) {

                    try {


                        elapsedTime = System.currentTimeMillis() - startTime
                        currentTime.value = workout.workOutPrimaryDetail[i.value].intervalDuration - elapsedTime

                        exercisesTotalTime= workout.workOutPrimaryDetail.subList(i.value,
                           workout.workOutPrimaryDetail.size).sumOf { it.intervalDuration } - elapsedTime

                        value.value = currentTime.value / workout.workOutPrimaryDetail[i.value].intervalDuration.toFloat()
                        value1.value = exercisesTotalTime / timeTotalList.toFloat()



                        if (currentTime.value in 0..3000L && !soundPlayed) {
                            playSound()
                            soundPlayed = true // Set the flag to true after playing the sound
                        }


                        if ( currentTime.value == 0L || currentTime.value < 0L ) {
                            soundPlayed=false


                            if(vibration){ vibrate()}

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



    fun vibrationModify(value:Boolean )= viewModelScope.launch  (Dispatchers.IO) {

        vibration = value

        Log.d("TBViewMode"," Vibration $value" )
    }


    fun ticModify(value:Long )= viewModelScope.launch  (Dispatchers.IO) {

        tic =value

        Log.d("TBViewMode"," Tic $tic" )
    }


    private fun vibrate( ) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500) // Deprecated in API 26
        }
    }

    private fun playSound() {


         val soundId = soundPool.load (context,R.raw.treescondscountdown, 1)

       soundPool.setOnLoadCompleteListener { _, _, _ ->
                soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            }

        }



    //  Pause Timer
    fun pauseTimer()= viewModelScope.launch  (Dispatchers.IO) {
        _pausedTime.value = System.currentTimeMillis()
        _isTimerRunning.value = false
        job?.cancel()
        Log.d("TBViewMode","isPause" )
    }


    //  Rest Timer
    fun resetTimer()= viewModelScope.launch  (Dispatchers.IO)  {

        job?.cancel()
        _playingWorkout.value =  null
        _isTimerRunning.value=false
        _showAlert.value=false
        _pausedTime.value =  null
        _elapsedTime.value=0
        currentTime.value=0
        value.value=0f
        value1.value=0f
        i.value=0
        Log.d("TBViewMode","reset Viewmodel")

    }



  //  Next Interval
   fun nextInterval()= viewModelScope.launch  (Dispatchers.IO)  {
        job?.cancel()

        soundPlayed=false

        i.value++
        goingForward.value=true
        startTimer()

        Log.d("TBViewMode","Next Interval")
    }


    //  Previous  Interval
    fun previousInterval()= viewModelScope.launch  (Dispatchers.IO)  {

        job?.cancel()
        soundPlayed=false
         i.value--
        goingForward.value=false
        startTimer()
        Log.d("TBViewMode","Previous Interval ")

    }





    fun retrieveW(warmUpD:Long , workOutD:Long  , restD:Long , workRestC:Int , restBtSets:Long , setsC :Int , coolDownD:Long)=viewModelScope.launch (Dispatchers.IO){
_playingWorkout.value=
  WorkOutDetail(workOutPrimaryDetail = workOutListBuilder(
      warmUpD = warmUpD,
      workOutD = workOutD,
      restD =  restD,
      workRestC =  workRestC,
      restBtSets = restBtSets,
      setsC = setsC,
      coolDownD =  coolDownD )
        )
                   }

    override fun onCleared() {
        super.onCleared()
        unregisterReceiver(context)
    }
}

