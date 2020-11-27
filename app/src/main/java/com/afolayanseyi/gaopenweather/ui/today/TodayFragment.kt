package com.afolayanseyi.gaopenweather.ui.today

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import com.afolayanseyi.gaopenweather.R
import com.afolayanseyi.gaopenweather.data.ResourceState
import com.afolayanseyi.gaopenweather.databinding.FragmentTodayBinding
import com.afolayanseyi.gaopenweather.extensions.getIconUrl
import com.afolayanseyi.gaopenweather.extensions.gone
import com.afolayanseyi.gaopenweather.extensions.inject
import com.afolayanseyi.gaopenweather.extensions.toFormattedString
import com.afolayanseyi.gaopenweather.extensions.visible
import com.afolayanseyi.gaopenweather.model.CurrentWeatherUI
import com.afolayanseyi.gaopenweather.ui.BaseFragment
import com.afolayanseyi.gaopenweather.util.DATE_FORMAT_DAY_MONTH_DAY
import kotlinx.android.synthetic.main.fragment_today.*


class TodayFragment : BaseFragment() {

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        inject()

        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        weatherViewModel.currentWeatherLiveData.observe(viewLifecycleOwner, { resource ->
            resource?.let {
                when (it.state) {
                    ResourceState.LOADING -> {
                        progress_circular.visible()
                    }
                    ResourceState.SUCCESS -> {
                        progress_circular.gone()
                        presentView(it.data)
                    }

                    ResourceState.ERROR -> {
                        progress_circular.gone()
                        text_error.apply {
                            visible()
                            text = it.exception?.message
                        }
                        presentErrorDialog("Error", it.exception?.message) {
                            fetchLocation()
                        }
                    }
                }
            }
        })

        weatherViewModel.addressLiveData.observe(viewLifecycleOwner, {
            text_view_place_name.text = it
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customiseSearchView()
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    handleAddressSearch(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    handleAddressSearch(it)
                }
                return true
            }

        })
    }

    private fun customiseSearchView() {
        val searchView = search_view
        val searchEditText =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.setTextColor(Color.BLACK)
        searchEditText.setHintTextColor(Color.LTGRAY)
        val searchPlate: View? = searchView.findViewById(androidx.appcompat.R.id.search_plate)
        searchPlate?.setBackgroundResource(R.drawable.searchview_background)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun presentView(data: CurrentWeatherUI?) {
        data?.let { currentWeather ->
            layout_header_view.visible()
            layout_weather_info.visible()
            text_view_date.text = currentWeather.date.toFormattedString(DATE_FORMAT_DAY_MONTH_DAY)

            text_view_weather_description.text = currentWeather.description?.capitalize()
            currentWeather.icon?.let {
                imageLoader.loadImage(getIconUrl(it), image_view_weather_icon)
            }
            text_view_temperature.text = currentWeather.temperature?.toInt()?.toString()
            text_view_feels_like_value.text =
                getString(
                    R.string.text_temp_celcius,
                    currentWeather.feelsLike?.toInt()?.toString()
                )
            text_view_wind_value.text =
                getString(
                    R.string.text_wind_speed,
                    currentWeather.wind?.toString()
                )
            text_view_humidity_value.text = currentWeather.humidity?.toString()?.plus("%")
            text_view_uv_value.text = currentWeather.uvIndex?.toInt()?.toString()
        } ?: kotlin.run {
            text_error.apply {
                visible()
                text = getString(R.string.error_message)
            }
        }
    }

    private fun handleAddressSearch(searchQuery: String) {
        if (isValidSearchAddress(searchQuery)) {
            weatherViewModel.getLocationFromAddress(searchQuery)
        }
    }

    private fun isValidSearchAddress(searchQuery: String) =
        searchQuery.isNotEmpty() && searchQuery.length > 3
}