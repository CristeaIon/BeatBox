package com.crion.beatbox

private const val WAV = ".wav"

class Sound(assetPath:String,var soundId: Int?= null) {
    val name = assetPath.split("/").last().removeSuffix(WAV)
}