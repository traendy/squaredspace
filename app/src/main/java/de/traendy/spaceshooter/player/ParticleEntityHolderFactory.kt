package de.traendy.spaceshooter.player

import android.graphics.Color
import android.graphics.Paint

object ParticleEntityHolderFactory {
    private val mPlayerPaint = Paint().apply {
        color = Color.parseColor("#5500AA33")
        strokeWidth = 10f
    }
    private val mPlayerBorderPaint = Paint().apply {
        color = Color.parseColor("#CC00AA33")
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    private val mBossPaint = Paint().apply {
        color = Color.parseColor("#55AA3300")
        strokeWidth = 10f
    }
    private val mBossBorderPaint = Paint().apply {
        color = Color.parseColor("#CCAA3300")
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    fun createPlayerParticleHolder():PlayerParticleEntityHolder{
        return PlayerParticleEntityHolder(mPlayerPaint, mPlayerBorderPaint)
    }

    fun createBossParticleHolder(): PlayerParticleEntityHolder {
        return PlayerParticleEntityHolder(mBossPaint, mBossBorderPaint)
    }
}