package com.enesyildirim.notes.common

import android.graphics.Color
import kotlin.random.Random

class Utils {
    fun getRandomColor() = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))

    fun getTimeInMillis() = System.currentTimeMillis()
}