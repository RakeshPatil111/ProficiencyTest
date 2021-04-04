package com.example.proficiencytest.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.proficiencytest.R

/**
 * Use this in xml for binding images
 * first parameter in view, second one is url of image
 * Here we have used cache for images so we can see them offline (Glide Cahche Strategy)
 */
@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String?) {
    if (url != null) {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.default_thumbnail)
            .apply(requestOptions).into(imageView)
    }
}