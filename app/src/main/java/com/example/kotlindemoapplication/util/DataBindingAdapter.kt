package com.example.kotlindemoapplication.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.kotlindemoapplication.R

object BindingAdapterUtils {

    @JvmStatic
    @BindingAdapter("image","placeholder")
    fun setImage(image: ImageView, url: String?, placeHolder: Drawable) {
        if (!url.isNullOrEmpty()){

            Glide.with(image.context).load(url).circleCrop()
                .placeholder(R.drawable.download)
                .into(image)
        }
        else{
            image.setImageDrawable(placeHolder)
        }
    }
}