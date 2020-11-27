package com.afolayanseyi.gaopenweather.ui.weekly

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.afolayanseyi.gaopenweather.OpenWeatherApplication
import com.afolayanseyi.gaopenweather.data.ResourceState
import com.afolayanseyi.gaopenweather.databinding.FragmentWeeklyBinding
import com.afolayanseyi.gaopenweather.di.DaggerAppComponent
import com.afolayanseyi.gaopenweather.extensions.getIconUrl
import com.afolayanseyi.gaopenweather.extensions.toFormattedString
import com.afolayanseyi.gaopenweather.model.FullWeeklyData
import com.afolayanseyi.gaopenweather.model.WeekDay
import com.afolayanseyi.gaopenweather.ui.BaseFragment
import com.afolayanseyi.gaopenweather.util.DATE_FORMAT_DAY_MONTH_DAY
import kotlinx.android.synthetic.main.fragment_weekly.*

class WeeklyFragment : BaseFragment() {

    private var _binding: FragmentWeeklyBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerAppComponent.builder()
            .application(OpenWeatherApplication.instance!!)
            .build()
            .inject(this)

        _binding = FragmentWeeklyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        weatherViewModel.weatherForecastLiveData.observe(viewLifecycleOwner, { resource ->
            resource?.let {
                when (it.state) {
                    ResourceState.LOADING -> {
                        Log.e("WEEK", "loading")
                    }
                    ResourceState.SUCCESS -> {
                        presentView(it.data)
                    }

                    ResourceState.ERROR -> {
                        presentErrorDialog("Error", it.exception?.message, null)
                    }
                }
            }
        })

        weatherViewModel.addressLiveData.observe(viewLifecycleOwner, {
            text_view_place_name.text = it
        })

        return root
    }

    private fun presentView(data: FullWeeklyData?) {
        data?.let { weeklyData ->
            weeklyData.currentWeatherUI?.let { currentWeather ->
                text_view_date.text =
                    currentWeather.date.toFormattedString(DATE_FORMAT_DAY_MONTH_DAY)
                text_view_weather_description.text = currentWeather.description?.capitalize()
                weeklyData.currentWeatherUI?.icon?.let {
                    imageLoader.loadImage(getIconUrl(it), image_view_weather_icon)
                }
                text_view_temperature.text = currentWeather.temperature?.toInt()?.toString()
            }
            recycler_view.apply {
                val itemList = mutableListOf<WeekDay>()
                weeklyData.weeklyData?.filter {
                    it.date > 0
                }?.iterator()?.let {
                    for (dailyItem in it) {
                        itemList.add(dailyItem)
                    }
                }
                adapter = WeekWeatherAdapter(itemList, imageLoader)
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}