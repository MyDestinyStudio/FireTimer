package my.destinyStudio.firetimer.screens.editworkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import my.destinyStudio.firetimer.components.IntervalsType
import my.destinyStudio.firetimer.data.IntervalsInfoIndexed
import my.destinyStudio.firetimer.data.WorkOutDetail
import my.destinyStudio.firetimer.data.WorkoutUiState
import my.destinyStudio.firetimer.repository.WorkoutsRepository
import my.destinyStudio.firetimer.utils.swap
import my.destinyStudio.firetimer.utils.toIndexedA
import my.destinyStudio.firetimer.utils.toNormal
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class EditWorkoutViewModel  @Inject constructor(private val repository: WorkoutsRepository) : ViewModel()  {


 private val _workoutA = MutableStateFlow <WorkOutDetail? >( null)
  val workout = _workoutA.asStateFlow()

 private val _workoutUI = MutableStateFlow  ( WorkoutUiState())
 val workoutUi = _workoutUI.asStateFlow()


  private val _totalDuration =    MutableStateFlow(0)

    val  totalDuration  =_totalDuration.asStateFlow()



  private  val _intervalsNumber  = MutableStateFlow(0)

    val  intervalsNumber  = _intervalsNumber.asStateFlow()


   private val _setsNumber  = MutableStateFlow(0)
    val  setsNumber  =  _setsNumber.asStateFlow()




    private val _workoutName = MutableStateFlow   ( "" )

    private val workoutUiName = _workoutName.asStateFlow()




    public override fun onCleared() {
        super.onCleared()
        // Reset state variables here
        _workoutA.value = null
        _workoutName.value = ""
        _workoutUI.value = WorkoutUiState()
        Log.d("T","Cleared")
    }

    fun undoChange()= viewModelScope.launch (Dispatchers.IO) {
        _workoutUI.value = WorkoutUiState(idW=  workout.value!!.id.toString(),nameW=  workout.value!!.workOutName,
            intervalList = toIndexedA(  workout.value!!.workOutPrimaryDetail)   )
    }

 fun removeInterval(index: Int) = viewModelScope.launch (Dispatchers.IO){
  _workoutUI.value = _workoutUI.value.copy( intervalList = _workoutUI.value.intervalList.toMutableList().also {  it.removeAt(index) }   )

     _totalDuration.value =  workoutUi.value.intervalList.sumOf { it.intervalDuration.toInt() }
     _intervalsNumber.value =   workoutUi.value.intervalList.size
     _setsNumber.value =  workoutUi.value.intervalList.count {   it.intervalType== IntervalsType.REST_BTW_SETS }+1


                                                                          }


 fun addInterval(index: Int,intervalsInfoI  : IntervalsInfoIndexed) = viewModelScope.launch (  Dispatchers.IO){

  _workoutUI .value =_workoutUI.value.copy(  intervalList = _workoutUI.value.intervalList.toMutableList().also {  it.add(index,intervalsInfoI) }  )

     _totalDuration.value =  workoutUi.value.intervalList.sumOf { it.intervalDuration.toInt() }
     _intervalsNumber.value =   workoutUi.value.intervalList.size
     _setsNumber.value =  workoutUi.value.intervalList.count {   it.intervalType== IntervalsType.REST_BTW_SETS+1 }
 }


 fun swapIntervalUp(indexOne: Int ) = viewModelScope.launch (Dispatchers.IO){

  _workoutUI .value =_workoutUI.value.copy(intervalList = _workoutUI.value.intervalList.toMutableList().also {if (indexOne > 0)it.swap(indexOne,indexOne-1 )})

     _totalDuration.value =  workoutUi.value.intervalList.sumOf { it.intervalDuration.toInt() }
     _intervalsNumber.value =   workoutUi.value.intervalList.size
     _setsNumber.value =  workoutUi.value.intervalList.count {   it.intervalType== IntervalsType.REST_BTW_SETS+1 }
 }


 fun swapIntervalDown(indexOne: Int ) = viewModelScope.launch (Dispatchers.IO){

 _workoutUI .value =_workoutUI.value.copy(  intervalList = _workoutUI.value.intervalList.toMutableList().also {
    if (indexOne <  _workoutUI.value.intervalList.size - 1)it .swap(   indexOne,  indexOne + 1   )
                                                                  }
                                                         )

                                                          }



 fun updateInterval(index: Int, intervalsInfoI  : IntervalsInfoIndexed) = viewModelScope.launch ( Dispatchers.IO){

  _workoutUI .value =_workoutUI.value.copy( intervalList = _workoutUI.value.intervalList.toMutableList().also {
      try{it[index] = intervalsInfoI } catch(_:IndexOutOfBoundsException){ } })

     _totalDuration.value =  workoutUi.value.intervalList.sumOf { it.intervalDuration.toInt() }
     _intervalsNumber.value =   workoutUi.value.intervalList.size
     _setsNumber.value =  workoutUi.value.intervalList.count {   it.intervalType== IntervalsType.REST_BTW_SETS+1 }
 }


    fun changeName (newName: String) = viewModelScope.launch (Dispatchers.IO){

        _workoutUI .value =_workoutUI.value.copy(  nameW =  newName   )
        _workoutName.value=newName


    }


 fun getWByIdd(id: String) = viewModelScope.launch(Dispatchers.IO) {

   repository.getWorkoutById(id).collect {
       workoutDetail ->
    _workoutA.value = workoutDetail
    _workoutUI.value = WorkoutUiState(idW= workoutDetail.id.toString(),nameW= workoutDetail.workOutName,intervalList = toIndexedA( workoutDetail.workOutPrimaryDetail)   )

       _workoutName.value = workoutDetail.workOutName
       _totalDuration.value =  workoutUi.value.intervalList.sumOf { it.intervalDuration.toInt() }
       _intervalsNumber.value =   workoutUi.value.intervalList.size
       _setsNumber.value =  workoutUi.value.intervalList.count {   it.intervalType== IntervalsType.REST_BTW_SETS+1 }
   }
  }


 fun updateWorkOut( ) = CoroutineScope(Dispatchers.IO).launch{

     val workoutToUpdate = WorkOutDetail(
        id =  UUID.fromString(workoutUi.value.idW),
        workOutName = workoutUiName.value,
        workOutPrimaryDetail = toNormal(workoutUi.value.intervalList)
     )
     repository.updateW(workoutToUpdate)


 }


}