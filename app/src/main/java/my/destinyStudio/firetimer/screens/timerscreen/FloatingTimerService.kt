package my.destinyStudio.firetimer.screens.timerscreen

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.core.app.NotificationCompat
import my.destinyStudio.firetimer.R

internal fun Context.findActivity(): ComponentActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is ComponentActivity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Picture in picture should be called in the context of an Activity")
}

class FloatingTimerService : Service() {

//    companion object {
//        fun startService(context: Context, innerL: Float, outerL: Float, timeLeft: Long) {
//            val intent = Intent(context, FloatingTimerService::class.java).apply {
//                putExtra("innerL", innerL)
//                putExtra("outerL", outerL)
//                putExtra("timeLeft", timeLeft)
//            }
//            context.startService(intent) // or context.startForegroundService(intent) if needed
//
//        }
//        fun stopService(context: Context) {
//            val intent = Intent(context, FloatingTimerService::class.java)
//            context.stopService(intent)
//        }
//    }




    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            Actions.START.toString() -> start()
            Actions.STOP.toString()  -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ForegroundServiceType")
    private fun start(){
        val notification = NotificationCompat
            .Builder(this,"running_timer")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("GG")
            .setContentText("Lets go 12 : 00 ")
            .build()
        startForeground ( 1,notification)
    }
    enum class Actions{
        START,STOP
    }

}
