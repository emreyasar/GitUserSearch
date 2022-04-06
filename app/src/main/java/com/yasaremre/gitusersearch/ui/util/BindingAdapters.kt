package com.yasaremre.gitusersearch.ui.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yasaremre.gitusersearch.R


/**
 * Binding adapters are used to bind data to UI components.
 */

/**
 * Fetches image from given URL and binds image to image view.
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        if (it.isNotEmpty()) {
            Glide.with(imgView.context)
                .load(imgUrl)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(imgView)
        }
    }
}