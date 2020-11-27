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
import com.afolayanseyi.gaopenweather.util.DATE_FORMAT_DAY_MONTH_DAY
import com.afolayanseyi.gaopenweather.util.DATE_FORMAT_DAY_ONLY
import com.afolayanseyi.gaopenweather.util.ImageLoader
import kotlinx.android.synthetic.main.item_daily_weather_forecast.view.*
import kotlinx.android.synthetic.main.item_daily_weather_forecast.view.image_view_weather_icon
import kotlinx.android.synthetic.main.item_weekly_header_layout.view.*

class WeekWeatherAdapter(
    private val weekDayList: List<WeekDay>,
    private val placeName: String?,
    private val imageLoader: ImageLoader
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_CURRENT_DAY = 1
        const val VIEW_TYPE_OTHER_DAYS = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View?
        return if (viewType == VIEW_TYPE_CURRENT_DAY) {
            view = inflater.inflate(R.layout.item_weekly_header_layout, parent, false)
            CurrentDayWeatherDataHolder(view)
        } else {
            view = inflater.inflate(R.layout.item_daily_weather_forecast, parent, false)
            WeatherDataHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WeatherDataHolder) {
            holder.bind(weekDayList[position])
            if (position == weekDayList.size - 1) {
                holder.itemView.view_line.gone()
            } else {
                holder.itemView.view_line.visible()
            }
        } else {
            (holder as CurrentDayWeatherDataHolder).bind(weekDayList[position])
        }
    }

    override fun getItemCount(): Int {
        return weekDayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_CURRENT_DAY
        } else {
            VIEW_TYPE_OTHER_DAYS
        }
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

    inner class CurrentDayWeatherDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weekDay: WeekDay) {
            itemView.apply {
                imageLoader.loadImage(getIconUrl(weekDay.icon!!), image_view_weather_icon)
                text_view_place_name.text = placeName ?: ""
                text_view_date.text =
                    weekDay.date.toFormattedString(DATE_FORMAT_DAY_MONTH_DAY)
                text_view_weather_description.text = weekDay.description?.capitalize()
                weekDay.icon?.let {
                    imageLoader.loadImage(getIconUrl(it), image_view_weather_icon)
                }
                text_view_temperature.text = weekDay.temperature?.toInt().toString()
            }
        }
    }
}