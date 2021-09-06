package com.crion.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

private const val TAG = "BeatBox"
private const val SOUND_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5

class BeatBox(private val assets:AssetManager) {
    val sounds:List<Sound>
    private val soundPool = SoundPool.Builder().setMaxStreams(MAX_SOUNDS).build()

    init {
        sounds = loadSounds()
    }
    fun play(sound: Sound){
        sound.soundId?.let {
            soundPool.play(it,1.0f,1.0f,0,1,1.0f)
        }
    }
    fun release(){
        soundPool.release()
    }
   private fun loadSounds():List<Sound>{
        val soundNames:Array<String>
         try {
             soundNames = assets.list(SOUND_FOLDER)!!

        }catch (e:Exception){
            Log.e(TAG,"Could not list assets",e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { fileName->
            val assetPath = "$SOUND_FOLDER/$fileName"
            val sound = Sound(assetPath)
            try {
                load(sound,"$SOUND_FOLDER/$fileName")
                sounds.add(sound)
            }catch (ioe:IOException){
                Log.e(TAG,"Could not load sound $fileName",ioe)
            }
        }
        return sounds
    }
    private fun load(sound: Sound, name: String){
        val afd:AssetFileDescriptor = assets.openFd(name)
        val soundId = soundPool.load(afd,1)
        sound.soundId = soundId
    }
}