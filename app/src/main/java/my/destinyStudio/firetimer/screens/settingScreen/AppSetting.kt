package my.destinyStudio.firetimer.screens.settingScreen



data class AppSettings(
 val notificationsEnabled: Boolean        = true,
 val vibrationEnabled: Boolean        = true ,
 val callPauseEnabled: Boolean        = true,
    val language: String                  = "en",
    val tic:Long                          = 100L,
    val defaultIntervalType : String      = "Work",
    val defaultIntervalDuration : Long    = 10000,
    val defaultIntervalName: String       = "Push Up",
    val defaultIntervalUri: String        = "",
 val defaultWarmUpDuration : Long    = 60000,
 val defaultWorkDuration : Long    = 10000,
 val defaultRestDuration : Long    = 10000,
 val  defaultRestBtwSetsDuration : Long    = 10000,
    val defaultCoolDownDuration : Long    = 10000,
 val defaultCycles : Long    = 5 ,
   val defaultSets : Long    = 3,
)