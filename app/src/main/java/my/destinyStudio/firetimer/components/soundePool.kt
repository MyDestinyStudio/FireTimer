package my.destinyStudio.firetimer.components

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import javax.inject.Inject


interface SoundPlayer {
    fun playSound(soundResourceId: Int)
}



class AndroidSoundPlayer @Inject constructor( private val context: Context ) : SoundPlayer {

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .setAudioAttributes(   AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()

    override fun playSound(soundResourceId: Int) {
        val soundId = soundPool.load(context, soundResourceId, 1)
        soundPool.setOnLoadCompleteListener { _, _, _ ->
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
        }
    }

    // Ensure resources are released when no longer needed
    fun release() {
        soundPool.release()
    }
}

//@Module
//@InstallIn(ActivityComponent::class)
//object SoundModule {
//    @Provides
//    fun provideSoundPlayer(@ApplicationContext context: Context): SoundPlayer {
//        return AndroidSoundPlayer(context)
//    }
//}