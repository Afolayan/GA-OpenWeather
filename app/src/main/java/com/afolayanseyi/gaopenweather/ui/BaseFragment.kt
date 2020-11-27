package com.afolayanseyi.gaopenweather.ui

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.afolayanseyi.gaopenweather.model.Coordinates
import com.afolayanseyi.gaopenweather.util.ImageLoader
import com.afolayanseyi.gaopenweather.util.LocationUtil
import com.google.android.gms.location.*
import javax.inject.Inject


const val REQUEST_CODE_LOCATION_PERMISSION = 200

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory

    val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(WeatherViewModel::class.java)
    }

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = (20 * 60 * 1000).toLong()
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        processLocation(location)
                    }
                }
            }
        }
        fetchLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mFusedLocationClient != null) {
            mFusedLocationClient!!.removeLocationUpdates(locationCallback);
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION
            && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            fetchLocation()
        }
    }

    fun presentErrorDialog(title: String, message: String?, retryAction: (() -> Unit?)?) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton("Okay") { dialog, _ ->
                dialog.cancel()
            }
        retryAction?.let {
            alertDialog
                .setPositiveButton("Retry") { dialog, _ ->
                    dialog.cancel()
                    retryAction.invoke()
                }
        }
        alertDialog
            .create()
            .show()
    }

    fun fetchLocation() {
        val context = requireContext()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE_LOCATION_PERMISSION
            )

            return
        }
        mFusedLocationClient?.apply {
            requestLocationUpdates(locationRequest, locationCallback, null)
            lastLocation.addOnSuccessListener(requireActivity()) { location ->
                if (location != null) {
                    processLocation(location)
                } else {
                    requestLocationUpdates(locationRequest, locationCallback, null)
                }
            }
        }
    }

    private fun processLocation(location: Location) {
        weatherViewModel.fetchWeatherBy(Coordinates(location.latitude, location.longitude))
        weatherViewModel.coordinateAddress =
            LocationUtil.getAddressFromLocation(
                requireContext(),
                location.latitude,
                location.longitude
            )
    }

}