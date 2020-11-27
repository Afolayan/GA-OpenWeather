package com.afolayanseyi.gaopenweather.extensions

import android.view.View
import com.afolayanseyi.gaopenweather.util.BASE_IMAGE_URL
import com.afolayanseyi.gaopenweather.util.ICON

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun getIconUrl(icon: String) = BASE_IMAGE_URL.replace(ICON, icon)