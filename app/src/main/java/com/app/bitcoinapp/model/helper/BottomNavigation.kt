package com.app.bitcoinapp.model.helper

import android.graphics.Color
import android.widget.ImageButton
import android.widget.TextView

class BottomNavigation {

    fun colorsNavigation(imageBtn: ImageButton, textView: TextView, color: String) {
        imageBtn.setColorFilter(Color.parseColor(color))
        textView.setTextColor(Color.parseColor(color))
    }
}