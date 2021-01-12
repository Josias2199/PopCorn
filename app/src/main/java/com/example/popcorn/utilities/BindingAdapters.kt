package com.example.popcorn.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.popcorn.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

object BindingAdapters {
    //companion object {
        @BindingAdapter("android:imageURL")
        @JvmStatic
        fun setImageURL(imageView: ImageView, URL: String?) {
            try {
                imageView.alpha = 0f
                Picasso.get().load(URL).placeholder(R.drawable.placeholder_image).noFade().into(imageView, object : Callback {
                    override fun onSuccess() {
                        imageView.animate().setDuration(300).alpha(1f).start()
                    }

                    override fun onError(e: Exception) {}
                })
            } catch (ignored: Exception) {
            }
        }
    //}
}