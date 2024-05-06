package com.example.session2.domain

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.OrientationEventListener
import android.widget.ImageView
import android.widget.LinearLayout

interface StarInerface {
    var starsss: Int
    fun addStars(container: LinearLayout){
        if (starsss in 0..4) {
            Log.e("Add stars", "true")
            (container.getChildAt(starsss) as ImageView).setColorFilter(Color.YELLOW)
            starsss++
            Log.e("Stars count", starsss.toString())
        }
    }
    fun removeStars(container: LinearLayout) {
        if (starsss in 1..5) {
            Log.e("Remove stars", "true")
            starsss--
            (container.getChildAt(starsss) as ImageView).setColorFilter(Color.GRAY)
            Log.e("Stars count", starsss.toString())
        }
    }
}