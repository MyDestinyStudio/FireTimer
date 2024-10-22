package my.destinyStudio.firetimer.screens.editworkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import my.destinyStudio.firetimer.components.IntervalsType
import my.destinyStudio.firetimer.data.IntervalsInfoIndexed
import my.destinyStudio.firetimer.data.WorkOutDetail
import my.destinyStudio.firetimer.data.WorkoutUiState
import my.destinyStudio.firetimer.repository.WorkoutsRepository
import my.destinyStudio.firetimer.utils.swap
import my.destinyStudio.firetimer.utils.toIndexed
import my.destinyStudio.firetimer.utils.toNormal
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class EditWorkoutViewModel  @Inject constructor(private val repository: WorkoutsRepository) : ViewModel()  {


 private val _workoutA = MutableStateFlow <WorkOutDetail? >( null)
 private val workout = _workoutA.asStateFlow()

 private val _workoutUI = MutableStateFlow  ( WorkoutUiState())
 val workoutUi = _workoutUI.asStateFlow()


  private val _totalDuration = MutableStateFlow(_workoutA.value?.workOutPrimaryDetail?.sumOf { it.intervalDuration.toInt() } ?: 0)

  val  totalDuration  =_totalDuration.asStateFlow()

  private  val _intervalsNumber  = MutableStateFlow(_workoutUI.value.intervalList.size)

    val  intervalsNumber  = _intervalsNumber.asStateFlow()


   private val _setsNumber  = MutableStateFlow(0)
    val  setsNumber  =  _setsNumber.asStateFlow()

   private val _workoutName = MutableStateFlow   ( "" )

   //  val workoutUiName = _workoutName.asStateFlow()

   // private var isLoad = mutableStateOf(false)

    init {
        Log.d("F","VM is initialized")
    }

//    public override fun onCleared() {
//        super.onCleared()
//     //    Reset state variables here
//        _workoutA.value = null
//        _workoutName.value = ""
//        _workoutUI.value = WorkoutUiState()
//        Log.d("T","VM is Cleared")
//    }


    fun undoChange()= viewModelScope.launch (Dispatchers.IO) {
        _workoutUI.value = WorkoutUiState(idW=  workout.value!!.id.toString(),nameW=  workout.value!!.workOutName,
            intervalList = toIndexed(  workout.value!!.workOutPrimaryDetail)   )
    }

 fun removeInterval(index: Int) = viewModelScope.launch (Dispatchers.IO){
  _workoutUI.value = _workoutUI.value.copy( intervalList = _workoutUI.value.intervalList.toMutableList().also {  it.removeAt(index) }   )

     _intervalsNumber.value= _workoutUI.value.intervalList.size
     _totalDuration.value= _workoutUI.value.intervalList.sumOf { it.intervalDuration }.toInt()
     _setsNumber.value= _workoutUI.value.intervalList.count { it.intervalType==IntervalsType.REST_BTW_SETS } + 1


                                         }


 fun addInterval(index: Int,intervalsInfoI  : IntervalsInfoIndexed) = viewModelScope.launch (  Dispatchers.IO){

  _workoutUI .value =_workoutUI.value.copy(  intervalList = _workoutUI.value.intervalList.toMutableList().also {  it.add(index,intervalsInfoI) }  )
     _intervalsNumber.value= _workoutUI.value.intervalList.size
     _totalDuration.value= _workoutUI.value.intervalList.sumOf { it.intervalDuration }.toInt()
     _setsNumber.value= _workoutUI.value.intervalList.count { it.intervalType==IntervalsType.REST_BTW_SETS } + 1


 }


 fun swapIntervalUp(indexOne: Int ) = viewModelScope.launch (Dispatchers.IO){

  _workoutUI .value =_workoutUI.value.copy(intervalList = _workoutUI.value.intervalList.toMutableList().also {if (indexOne > 0)it.swap(indexOne,indexOne-1 )})


 }


 fun swapIntervalDown(indexOne: Int ) = viewModelScope.launch (Dispatchers.IO){

 _workoutUI .value =_workoutUI.value.copy(  intervalList = _workoutUI.value.intervalList.toMutableList().also {
    if (indexOne< _workoutUI.value.intervalList.size-1)it .swap(indexOne,indexOne+1)
 }
                                                         )
                                                       }



 fun updateInterval(index: Int, intervalsInfoI  : IntervalsInfoIndexed) = viewModelScope.launch ( Dispatchers.IO){

  _workoutUI .value =_workoutUI.value.copy( intervalList = _workoutUI.value.intervalList.toMutableList().also {
      try{it[index] = intervalsInfoI } catch(_:IndexOutOfBoundsException){ } })

     _intervalsNumber.value= _workoutUI.value.intervalList.size
     _totalDuration.value= _workoutUI.value.intervalList.sumOf { it.intervalDuration }.toInt()
     _setsNumber.value= _workoutUI.value.intervalList.count { it.intervalType==IntervalsType.REST_BTW_SETS } + 1
 }





 fun getWByIdd(id: String) = viewModelScope.launch(Dispatchers.IO) {

     _workoutA.value = repository.getWorkoutById(id).first()
     _workoutUI.value = WorkoutUiState(
         idW=  _workoutA.value!!.id.toString(),
         nameW= _workoutA.value!!.workOutName,
         intervalList = toIndexed( _workoutA.value!!.workOutPrimaryDetail))


                                              }

    fun changeName (newName: String) = viewModelScope.launch (Dispatchers.IO){

        _workoutUI .value =_workoutUI.value.copy(  nameW =  newName   )
        _workoutName.value=newName


    }

 fun updateWorkOut() = viewModelScope.launch(Dispatchers.IO){

     val workoutToUpdate = WorkOutDetail(
        id =UUID.fromString(workoutUi.value.idW),
        workOutName = _workoutUI.value.nameW.toString(),
        workOutPrimaryDetail = toNormal(workoutUi.value.intervalList)
     )

     repository.updateW(workoutToUpdate)



 }


}