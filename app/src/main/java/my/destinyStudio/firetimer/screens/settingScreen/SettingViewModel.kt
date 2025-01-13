package my.destinyStudio.firetimer.screens.settingScreen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import my.destinyStudio.firetimer.data.IntervalsInfo
import javax.inject.Inject


val NOTIFICATIONS_ENABLED_KEY = booleanPreferencesKey("notifications_enabled")
val VIBRATION_KEY = booleanPreferencesKey("vibration_enabled")
val CALL_KEY = booleanPreferencesKey("call_enabled")
val LANGUAGE_KEY = stringPreferencesKey("language")
val TIC_KEY = longPreferencesKey("tic")
val INTERVALS_TYPE_KEY = stringPreferencesKey("intervals_type")
val INTERVALS_DURATION_KEY = longPreferencesKey("intervals_duration")
val INTERVALS_NAME_KEY = stringPreferencesKey("intervals_name")
val INTERVALS_URI_KEY = stringPreferencesKey("intervals_uri")

val WARMUP_DURATION_KEY = longPreferencesKey("default_warm_up_duration")
val WORK_DURATION_KEY = longPreferencesKey("default_work_out_duration")
val REST_DURATION_KEY = longPreferencesKey("default_rest_duration")
val REST_BTW_SETS_DURATION_KEY = longPreferencesKey("default_rest_btw_sets_duration")
val COOLDOWN_DURATION_KEY = longPreferencesKey("default_cool_down_duration")
val SETS_KEY = longPreferencesKey("default_sets")
val CYCLES_KEY = longPreferencesKey("default_cycles")



  val Context.dataStore  by preferencesDataStore(name = "app_settings")
@HiltViewModel

class SettingsViewModel @Inject constructor(
              private val context: Context
                ) : ViewModel() {



    private var _settings = MutableStateFlow (AppSettings())
    val settings: StateFlow<AppSettings > = _settings.asStateFlow()

    private var _settingsLoaded = MutableStateFlow(false)
    val settingsLoaded: StateFlow<Boolean> = _settingsLoaded.asStateFlow()

    private var _pageIndex= MutableStateFlow(2)
    val pageIndex : StateFlow<Int> = _pageIndex.asStateFlow()



    init {
        viewModelScope.launch {
            context.dataStore.data.collect { preferences ->
                _settings.value = AppSettings(
                    notificationsEnabled = preferences[NOTIFICATIONS_ENABLED_KEY] ?: true,
                    vibrationEnabled = preferences[VIBRATION_KEY] ?: true,
                    callPauseEnabled =   if(isPermissionsForPhoneCallGranted.value){ preferences[CALL_KEY]} else {false}?:false  ,
                    language = preferences[LANGUAGE_KEY] ?: "en",
                    tic = preferences[TIC_KEY] ?: 100L,
                    defaultIntervalDuration =    preferences[INTERVALS_DURATION_KEY]?:10000L ,
                    defaultIntervalName =    preferences[INTERVALS_NAME_KEY]?: "",
                    defaultIntervalType =  preferences[INTERVALS_TYPE_KEY]?: "Rest",
                    defaultIntervalUri =  preferences[INTERVALS_URI_KEY]?: "",
                    defaultWarmUpDuration = preferences[WARMUP_DURATION_KEY]?:30000L,
                    defaultWorkDuration = preferences[WORK_DURATION_KEY]?:30000L,
                    defaultRestDuration =preferences[REST_DURATION_KEY]?:30000L ,
                    defaultRestBtwSetsDuration = preferences[REST_BTW_SETS_DURATION_KEY]?:30000L,
                    defaultCoolDownDuration =preferences[COOLDOWN_DURATION_KEY]?:30000L ,
                    defaultSets = preferences[SETS_KEY]?:5L,
                    defaultCycles =preferences[CYCLES_KEY]?:2L


                )

                _settingsLoaded.value=true

                Log.d("settingVM","appS  ${_settingsLoaded.value}")
                Log.d("settingVM","appS is collected ${_settings.value}")
            }


        }

        Log.d("settingVM","ViewModel is initiaized ")
    }



    fun indexPage(i : Int ) =viewModelScope.launch {
        _pageIndex.emit(i)
        Log.d("settingVM","${_pageIndex.value}")

    }


    fun updateNotifications(enabled: Boolean) =viewModelScope.launch {
        context.dataStore.edit { preferences ->
                preferences[NOTIFICATIONS_ENABLED_KEY] = enabled
            }
        }
    fun updateVibration(enabled: Boolean) =viewModelScope.launch {
        context. dataStore.edit { preferences ->
            preferences[VIBRATION_KEY] = enabled
        }
    }
    fun updateCall(enabled: Boolean) =viewModelScope.launch {
        context.dataStore.edit { preferences ->
            preferences[CALL_KEY] = enabled
        }
        context.dataStore.data.collect { preferences ->
            _settings.value = AppSettings(
                notificationsEnabled = preferences[NOTIFICATIONS_ENABLED_KEY] ?: true,
                vibrationEnabled = preferences[VIBRATION_KEY] ?: true,
                callPauseEnabled =   preferences[CALL_KEY] ?: true,
                language = preferences[LANGUAGE_KEY] ?: "en",
                tic = preferences[TIC_KEY] ?: 100L,
                defaultIntervalDuration =    preferences[INTERVALS_DURATION_KEY]?:10000L ,
                defaultIntervalName =    preferences[INTERVALS_NAME_KEY]?: "",
                defaultIntervalType =  preferences[INTERVALS_TYPE_KEY]?: "Rest",
                defaultIntervalUri =  preferences[INTERVALS_URI_KEY]?: "",
                defaultWarmUpDuration = preferences[WARMUP_DURATION_KEY]?:30000L,
                defaultWorkDuration = preferences[WORK_DURATION_KEY]?:30000L,
                defaultRestDuration =preferences[REST_DURATION_KEY]?:30000L ,
                defaultRestBtwSetsDuration = preferences[REST_BTW_SETS_DURATION_KEY]?:30000L,
                defaultCoolDownDuration =preferences[COOLDOWN_DURATION_KEY]?:30000L ,
                defaultSets = preferences[SETS_KEY]?:5L,
                defaultCycles =preferences[CYCLES_KEY]?:2L


            )
         Log.d("settingVM","appS call edit  ")

        }
    }


    fun updateLanguage(language: String)=viewModelScope.launch {
        context.dataStore.edit { preferences ->
                preferences[LANGUAGE_KEY] = language
            }
        Log.d("settingVM","Up Language ${_settings.value.language}")

        }
    fun updateTic(tic:Long)=viewModelScope.launch {
        context.dataStore.edit { preferences ->
            preferences[TIC_KEY] =  tic
        }
        Log.d("settingVM","Up Tic ${_settings.value.tic}")

    }
    fun updateInterval(intervalsInfo: IntervalsInfo )=viewModelScope.launch {
        context.dataStore.edit { preferences ->

            preferences[INTERVALS_DURATION_KEY] =    intervalsInfo.intervalDuration
              preferences[INTERVALS_NAME_KEY]=    intervalsInfo.intervalName
            preferences[INTERVALS_TYPE_KEY]=    intervalsInfo.intervalType
            preferences[INTERVALS_URI_KEY]=    intervalsInfo.uri.toString()

        }
        Log.d("settingVM","Up DefaultInterval ${_settings.value.defaultIntervalName}" +
                "${_settings.value.defaultIntervalDuration}" +
                (_settings.value.defaultIntervalType) +
                (_settings.value.defaultIntervalUri)
        )

    }

    fun updateDefaultWorkout( defaultWorkout :DefaultWorkoutParameter )=viewModelScope.launch {
        context.dataStore.edit { preferences ->
                  preferences[WARMUP_DURATION_KEY]  = defaultWorkout.warmUpDuration
                  preferences[WORK_DURATION_KEY] = defaultWorkout.workOutDuration
                  preferences[REST_DURATION_KEY] = defaultWorkout.restDuration
                  preferences[REST_BTW_SETS_DURATION_KEY] = defaultWorkout.restBtwSetsDuration
                  preferences[COOLDOWN_DURATION_KEY] = defaultWorkout.cooldownDuration
                  preferences[SETS_KEY]  =defaultWorkout.setsNumbers.toLong()
                  preferences[CYCLES_KEY] =defaultWorkout.cycleNumbers.toLong()


        }

        Log.d("settingVM","Up workout " +
                "${settings.value.defaultWarmUpDuration} " +
                "${ settings.value.defaultWorkDuration  } " +
                "${ settings.value.defaultRestDuration  } " +
                "${ settings.value.defaultRestBtwSetsDuration } " +
                "${ settings.value.defaultCoolDownDuration } " +
                "${ settings.value.defaultCycles} " +
                "${ settings.value.defaultSets } ")


    }
//Permissions



    private var _isPermissionsForPhoneCallGranted =
        MutableStateFlow (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)


   val  isPermissionsForPhoneCallGranted : StateFlow<Boolean > = _isPermissionsForPhoneCallGranted.asStateFlow()

    private var _visiblePermissionDialogQueue= MutableStateFlow ( mutableListOf( ""))
    val visiblePermissionDialogQueue : StateFlow<MutableList<String>> = _visiblePermissionDialogQueue.asStateFlow()

  //  val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog()=viewModelScope.launch {
        _visiblePermissionDialogQueue.value.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) =viewModelScope.launch{
        if(!isGranted && !_visiblePermissionDialogQueue.value.contains(permission)) {
            _visiblePermissionDialogQueue.value.add(permission)
        }
    }

    fun onOnePermissionResult(   permission: String,  isGranted: Boolean   ) =viewModelScope.launch{

       when(permission) {
           Manifest.permission.READ_PHONE_STATE ->  if(isGranted)_isPermissionsForPhoneCallGranted.value = isGranted else updateCall(false)

           else->{  Log.d("settingVM","noResult")}

       }
            // ContextCompat.checkSelfPermission(context,permission ) == PackageManager.PERMISSION_GRANTED

    }


    private var _permissionsTextProvider = MutableStateFlow<PermissionTextProvider> (PhoneCallPermissionTextProvider() )


    val  permissionsTextProvider : StateFlow< PermissionTextProvider > = _permissionsTextProvider.asStateFlow()

    fun   dialogPermissionTextProvider (  permissionTextProvider: PermissionTextProvider =PhoneCallPermissionTextProvider() )=viewModelScope.launch{

        _permissionsTextProvider.value= permissionTextProvider



    }




    }








fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}