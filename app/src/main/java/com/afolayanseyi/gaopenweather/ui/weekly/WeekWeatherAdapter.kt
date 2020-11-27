package com.afolayanseyi.gaopenweather.ui.weekly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afolayanseyi.gaopenweather.R
import com.afolayanseyi.gaopenweather.extensions.getIconUrl
import com.afolayanseyi.gaopenweather.extensions.gone
import com.afolayanseyi.gaopenweather.extensions.toFormattedString
import com.afolayanseyi.gaopenweather.extensions.visible
import com.afolayanseyi.gaopenweather.model.WeekDay
import com.afolayanseyi.gaopenweather.util.DATE_FORMAT_DAY_ONLY
import com.afolayanseyi.gaopenweather.util.ImageLoader
import kotlinx.android.synthetic.main.item_daily_weather_forecast.view.*

class WeekWeatherAdapter(
    private val weekDayList: List<WeekDay>,
    private val imageLoader: ImageLoader
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_daily_weather_forecast, parent, false)
        return WeatherDataHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WeatherDataHolder) {
            holder.bind(weekDayList[position])
            if (position == weekDayList.size - 1) {
                holder.itemView.view_line.gone()
            } else {
                holder.itemView.view_line.visible()
            }
        }
    }

    override fun getItemCount(): Int {
        return weekDayList.size
    }

    inner class WeatherDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weekDay: WeekDay) {
            itemView.apply {
                imageLoader.loadImage(getIconUrl(weekDay.icon!!), image_view_weather_icon)
                text_view_day.text = weekDay.date.toFormattedString(DATE_FORMAT_DAY_ONLY)
                text_view_temperatures.text = context.getString(
                    R.string.temperature_data,
                    weekDay.minTemp?.toInt(), weekDay.maxTemp?.toInt()
                )
                text_view_description.text = weekDay.description
            }
        }
    }
}