package my.destinyStudio.firetimer.navigation


sealed class Screens(val route: String) {
    data object StartScreen :Screens (  "start_screen" )
    data object TimerBScreen  :Screens ( "timer_b_screen")
    data object TimerScreen  :Screens ( "timer_screen")
    data object CalenderScreen  :Screens (  "interval_screen")
    data object SavedWorkOutScreen:Screens (  "saved_workout_screen")
    data object SavedWorkOutEditScreen :Screens (  "saved_workout_edit_screen")
    data object InfoScreen :Screens ( "info_screen")
    data object SettingScreen :Screens ( "setting_screen")
    data object TimerCScreen :Screens ( "timer_c_screen")


}
