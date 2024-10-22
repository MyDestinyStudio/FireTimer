package my.destinyStudio.firetimer.screens.settingScreen

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences


//import androidx.datastore.preferences.core

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "settings")



class SettingsViewModel(private val context: Context) : ViewModel() {

    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    init {
        viewModelScope.launch {
            context.dataStore.data.collect { preferences ->
                _settings.value = AppSettings(
                     notificationsEnabled = preferences[booleanPreferencesKey("notificationsEnabled")] ?: true,
                    language = preferences[stringPreferencesKey("language")] ?: "en"
                )
            }
        }
    }



    fun updateNotifications(enabled: Boolean) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[booleanPreferencesKey("notificationsEnabled")] = enabled
            }
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[stringPreferencesKey("language")] = language
            }
        }
    }
}