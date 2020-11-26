package com.afolayanseyi.gaopenweather.ui.weekly

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.afolayanseyi.gaopenweather.OpenWeatherApplication
import com.afolayanseyi.gaopenweather.data.ResourceState
import com.afolayanseyi.gaopenweather.databinding.FragmentWeeklyBinding
import com.afolayanseyi.gaopenweather.di.DaggerAppComponent
import com.afolayanseyi.gaopenweather.ui.WeatherViewModel
import com.afolayanseyi.gaopenweather.ui.WeatherViewModelFactory
import javax.inject.Inject

class WeeklyFragment : Fragment() {

    private var _binding: FragmentWeeklyBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory

    private val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(WeatherViewModel::class.java)
    }


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

        val textView: TextView = binding.textDashboard

        weatherViewModel.weatherForecastLiveData.observe(viewLifecycleOwner, { resource ->
            resource?.let {
                when (it.state) {
                    ResourceState.LOADING -> {
                        Log.e("WEEK", "loading")
                    }
                    ResourceState.SUCCESS -> {
                        Log.e("WEEK", "on success ${it.data}")
                    }

                    ResourceState.ERROR -> {
                        Log.e("WEEK", "on error ${it.exception}")
                    }
                }
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}