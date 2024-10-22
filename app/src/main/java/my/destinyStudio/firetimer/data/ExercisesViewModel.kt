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
import my.destinyStudio.firetimer.utils.workOutListBuilder
import javax.inject.Inject


@HiltViewModel
    class ExercisesViewModel @Inject constructor(private val repository: WorkoutsRepository) : ViewModel() {

        private val _workouts = MutableStateFlow<List<WorkOutDetail>>(emptyList())
        val workoutList = _workouts.asStateFlow()
    private val _workout  = MutableStateFlow< WorkOutDetail?  >( null )
    val workout  = _workout .asStateFlow()


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
    fun loadW(warmUpD:Long , workOutD:Long  , restD:Long , workRestC:Int , restBtSets:Long , setsC :Int , coolDownD:Long)=viewModelScope.launch (Dispatchers.IO){
        _workout.value=
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

        fun addWorkOut(workoutDetail:WorkOutDetail) = CoroutineScope(Dispatchers.IO).launch{ repository.addW(workoutDetail) }


         fun removeWorkOutById (id:String) = CoroutineScope(Dispatchers.IO).launch{repository.deleteByID(id) }
    fun removeWorkOutAll ( ) = CoroutineScope(Dispatchers.IO).launch{repository.deleteAllW()}




}
