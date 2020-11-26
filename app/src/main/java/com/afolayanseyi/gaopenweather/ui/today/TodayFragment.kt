package com.afolayanseyi.gaopenweather.ui.today

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.afolayanseyi.gaopenweather.OpenWeatherApplication
import com.afolayanseyi.gaopenweather.data.ResourceState
import com.afolayanseyi.gaopenweather.databinding.FragmentTodayBinding
import com.afolayanseyi.gaopenweather.di.DaggerAppComponent
import com.afolayanseyi.gaopenweather.model.Coordinates
import com.afolayanseyi.gaopenweather.ui.WeatherViewModel
import com.afolayanseyi.gaopenweather.ui.WeatherViewModelFactory
import javax.inject.Inject

class TodayFragment : Fragment() {

    private var _binding: FragmentTodayBinding? = null
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
    ): View {

        DaggerAppComponent.builder()
            .application(OpenWeatherApplication.instance!!)
            .build()
            .inject(this)

        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        weatherViewModel.currentWeatherLiveData.observe(viewLifecycleOwner, { resource ->
            resource?.let {
                when (it.state) {
                    ResourceState.LOADING -> {
                        Log.e("TODAY", "loading")
                    }
                    ResourceState.SUCCESS -> {
                        Log.e("TODAY", "on success ${it.data}")
                    }

                    ResourceState.ERROR -> {
                        Log.e("TODAY", "on error ${it.exception}")
                    }
                }
            }
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherViewModel.fetchWeatherBy(Coordinates(0.0, 0.0))
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}