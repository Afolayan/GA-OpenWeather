package com.afolayanseyi.gaopenweather.imageloader

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(url: String, view: ImageView)
}
