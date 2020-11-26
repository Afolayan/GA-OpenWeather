package com.afolayanseyi.gaopenweather.util

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(url: String, view: ImageView)
}
