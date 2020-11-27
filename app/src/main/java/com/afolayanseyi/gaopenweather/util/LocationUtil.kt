package com.afolayanseyi.gaopenweather.util

import android.content.Context
import android.location.Geocoder
import java.io.IOException
import java.util.*

object LocationUtil {
    fun getAddressFromLocation(
        context: Context,
        latitude: Double,
        longitude: Double
    ): String {
        val geocoder = Geocoder(context, Locale.ENGLISH)
        return try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.size > 0) {
                val fetchedAddress = addresses[0]
                val strAddress = StringBuilder()
                for (i in 0 until fetchedAddress.maxAddressLineIndex) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append(" ")
                }
                strAddress.toString()
            } else {
                "Searching Current Address"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            "Could not get address..!"
        }
    }
}