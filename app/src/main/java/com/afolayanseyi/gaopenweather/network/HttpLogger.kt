package com.afolayanseyi.gaopenweather.network

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(veryLongString: String) {
        val maxLogSize = 1000
        for (i in 0..veryLongString.length / maxLogSize) {
            val start = i * maxLogSize
            var end = (i + 1) * maxLogSize
            end = if (end > veryLongString.length) veryLongString.length else end
            val message = veryLongString.substring(start, end)
            Log.d("HttpLogger", message)
        }
    }
}