package com.example.popcorn.ui.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.databinding.ItemContainerSliderImageBinding
import com.example.popcorn.ui.home.detail.ImageSliderAdapter.ImageSliderViewHolder

class ImageSliderAdapter(private val sliderImages: List<String>) :
    RecyclerView.Adapter<ImageSliderViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemContainerSliderImageBinding = DataBindingUtil.inflate(
            layoutInflater!!, R.layout.item_container_slider_image, parent, false
        )
        return ImageSliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        holder.bindSliderImage(sliderImages[position])
    }

    override fun getItemCount(): Int {
        return sliderImages.size
    }

    class ImageSliderViewHolder(private val binding: ItemContainerSliderImageBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {

        fun bindSliderImage(imageURL: String) {
            binding.imageUrl = imageURL
        }
    }
}