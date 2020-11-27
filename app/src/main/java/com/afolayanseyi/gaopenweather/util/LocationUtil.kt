package com.afolayanseyi.gaopenweather.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.afolayanseyi.gaopenweather.OpenWeatherApplication
import com.afolayanseyi.gaopenweather.model.LatLng
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
                    if (it.locality.isNotEmpty()) {
                        strAddress.append(it.locality).append(", ")
                    } else if (it.adminArea.isNotEmpty()) {
                        strAddress.append(it.adminArea).append(", ")
                    }
                    strAddress.append(it.countryName)
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

    fun getLocationFromAddress(address: String): Single<LatLng> {
        val geocoder = Geocoder(OpenWeatherApplication.instance)
        val addresses: List<Address>
        var latLng: LatLng? = null

        try {
            addresses = geocoder.getFromLocationName(address, 5)
            if (addresses.isNotEmpty()) {
                addresses?.let {
                    it[0]?.let { singleAddress ->
                        latLng = LatLng(singleAddress.latitude, singleAddress.longitude)
                    }
                }
            } else {
                latLng = LatLng(0.0, 0.0)
            }
        } catch (exception: Exception) {
            latLng = LatLng(0.0, 0.0)
        }
        return Single.just(latLng)
    }
}