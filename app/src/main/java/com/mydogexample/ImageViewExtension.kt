package com.mydogexample

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.fromUrl(url: String) {
//    Picasso.get().load(url).into(this)
    Glide.with(context)
        .load(url)
        .into(this)
}