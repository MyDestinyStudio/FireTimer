package my.destinyStudio.firetimer.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import my.destinyStudio.firetimer.repository.WorkoutsRepository
import javax.inject.Inject


@HiltViewModel
    class ExercisesViewModel @Inject constructor(private val repository: WorkoutsRepository) : ViewModel() {

        private val _workouts = MutableStateFlow<List<WorkOutDetail>>(emptyList())
        val workoutList = _workouts.asStateFlow()
 //   private val _workout = MutableStateFlow <WorkOutDetail? >( null)
   //  val workout = _workout.asStateFlow()


  //  private val _workoutUI = MutableStateFlow  ( WorkoutUiState())

  //val workoutUi = _workoutUI.asStateFlow()










    init {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAll().distinctUntilChanged()
                    .collect { workouts ->
                        if (workouts.isEmpty()) {
                            _workouts.value = mutableListOf()
                        }else {
                            _workouts.value =  workouts
                        }

                    }

            }

        }


//    fun getWByIdd(id: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//           repository.getWorkoutById(id).collect { workoutDetail ->
//                _workout.value = workoutDetail
//               _workoutUI.value = WorkoutUiState(idW=_workout.value?.id.toString(),nameW= _workout.value?.workOutName
//                   ,intervalList = toIndexedA( _workout.value!!.workOutPrimaryDetail)
//               )
//
//            }
//        }
//    }

        fun addWorkOut(workoutDetail:WorkOutDetail) = CoroutineScope(Dispatchers.IO).launch{ repository.addW(workoutDetail) }
//        fun updateWorkOut(workoutDetail:WorkOutDetail) = CoroutineScope(Dispatchers.IO).launch{repository.updateW(workoutDetail) }
      //  fun removeWorkOut(workoutDetail:WorkOutDetail) = CoroutineScope(Dispatchers.IO).launch{ repository.deleteW(workoutDetail) }

         fun removeWorkOutById (id:String) = CoroutineScope(Dispatchers.IO).launch{repository.deleteByID(id) }


    }
