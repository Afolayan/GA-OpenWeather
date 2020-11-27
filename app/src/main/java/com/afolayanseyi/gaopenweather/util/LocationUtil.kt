package com.afolayanseyi.gaopenweather.util

import android.content.Context
import android.location.Geocoder
import io.reactivex.Single
import java.io.IOException
import java.util.*

object LocationUtil {
    fun getAddressFromLocation(
        context: Context,
        latitude: Double,
        longitude: Double
    ): Single<String> {
        val geocoder = Geocoder(context, Locale.ENGLISH)
        return try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.size > 0) {
                val fetchedAddress = addresses[0]
                val strAddress = StringBuilder()
                fetchedAddress?.let {
                    strAddress.append(fetchedAddress.locality)
                        .append(", ")
                        .append(fetchedAddress.countryName)
                }
                Single.just(strAddress.toString())
            } else {
                Single.just("Searching Current Address")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Single.just("Could not get address..!")
        }
    }
}