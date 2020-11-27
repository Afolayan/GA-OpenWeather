package com.afolayanseyi.gaopenweather.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Long.toDate(): Date {
    return Date(TimeUnit.SECONDS.toMillis(this))
}

fun Date.toFormattedString(format: String): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}

fun Long.toFormattedString(format: String): String {
    return this.toDate().toFormattedString(format)
}