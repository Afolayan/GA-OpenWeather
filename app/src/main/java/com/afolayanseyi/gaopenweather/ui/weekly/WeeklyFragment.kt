package com.afolayanseyi.gaopenweather.ui.weekly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.afolayanseyi.gaopenweather.data.ResourceState
import com.afolayanseyi.gaopenweather.databinding.FragmentWeeklyBinding
import com.afolayanseyi.gaopenweather.extensions.gone
import com.afolayanseyi.gaopenweather.extensions.inject
import com.afolayanseyi.gaopenweather.extensions.visible
import com.afolayanseyi.gaopenweather.model.WeatherUIData
import com.afolayanseyi.gaopenweather.model.WeekDay
import com.afolayanseyi.gaopenweather.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_weekly.*

class WeeklyFragment : BaseFragment() {

    private var _binding: FragmentWeeklyBinding? = null

    private val binding get() = _binding!!

    private lateinit var weekWeatherAdapter: WeekWeatherAdapter
    private var itemList = mutableListOf<WeekDay>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        inject()

        _binding = FragmentWeeklyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        weekWeatherAdapter =
            WeekWeatherAdapter(itemList, weatherViewModel.coordinateAddress, imageLoader)
        weatherViewModel.weatherForecastLiveData.observe(viewLifecycleOwner, { resource ->
            resource?.let {
                when (it.state) {
                    ResourceState.LOADING -> {
                        progress_circular.visible()
                        recycler_view.gone()
                    }
                    ResourceState.SUCCESS -> {
                        progress_circular.gone()
                        presentView(it.data)
                    }

                    ResourceState.ERROR -> {
                        progress_circular.gone()
                        presentErrorDialog("Error", it.exception?.message, null)
                    }
                }
            }
        })

        weatherViewModel.addressLiveData.observe(viewLifecycleOwner, {
            weekWeatherAdapter.notifyDataSetChanged()
        })

        return root
    }

    private fun presentView(data: WeatherUIData?) {
        recycler_view.visible()
        data?.let { weeklyData ->
            recycler_view.apply {
                weeklyData.weeklyData?.filter {
                    it.date > 0
                }?.iterator()?.let {
                    for (dailyItem in it) {
                        itemList.add(dailyItem)
                    }
                }
                adapter = weekWeatherAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}