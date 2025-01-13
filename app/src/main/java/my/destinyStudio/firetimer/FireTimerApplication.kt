package my.destinyStudio.firetimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class FireTimerApplication:Application()
{

    override fun onCreate() {
        super.onCreate()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "running_timer",
                "Running Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
       Lingver.init(this)
    }
}