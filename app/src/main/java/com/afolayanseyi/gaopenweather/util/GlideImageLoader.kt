package com.afolayanseyi.gaopenweather.util

import android.widget.ImageView
import com.afolayanseyi.gaopenweather.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class GlideImageLoader : ImageLoader {

    override fun loadImage(url: String, view: ImageView) {
        val options = RequestOptions().apply {
            error(R.drawable.ic_cloud_error)
            placeholder(R.drawable.ic_cloud_error)
            diskCacheStrategy(DiskCacheStrategy.ALL)
        }

        Glide.with(view)
            .load(url)
            .apply(options)
            .into(view)
    }


}