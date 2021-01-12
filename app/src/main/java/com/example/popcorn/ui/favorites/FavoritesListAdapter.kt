package com.example.popcorn.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.databinding.ItemContainerTvShowBinding
import com.example.popcorn.data.local.entity.TVShow



class FavoritesListAdapter(private val tvShows: List<TVShow>, private val favoritesListener: FavoritesListener) :
    RecyclerView.Adapter<FavoritesListAdapter.FavoritesTVShowViewHolder>() {

    private var layoutInflater: LayoutInflater? = null
    //private val favoritesListener: FavoritesListener

    //private lateinit var binding: ItemContainerTvShowBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesTVShowViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: ItemContainerTvShowBinding = DataBindingUtil.inflate(
            layoutInflater!!, R.layout.item_container_tv_show, parent, false
        )

        return FavoritesTVShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesTVShowViewHolder, position: Int) {
        holder.bindTVShow(tvShows[position])
        holder.itemView.setOnClickListener {
            val action = FavoritesFragmentDirections.actionNavigationFavoritesToDetailFragment(tvShows[position])
            it.findNavController().navigate(action)
        }

        /*holder.binding.ivDelete.setOnClickListener {
            favoritesListener.removeTVShowFromFavoritesList(tvShows[position],position)
        }*/


        /*holder.itemView.setOnClickListener {
            favoritesListener.onTVShowClicked(tvShows[position])
            favoritesListener.removeTVShowFromFavoritesList(tvShows[position], position)
        }*/

        /*holder.itemView.setOnClickListener {
            //with(tvShows[position]) {
                //val bundle = bundleOf("tvShow_" to tvShows[position])
                /*val action =
                    PrincipalFragmentDirections.actionNavigationHomeToDetailFragment(
                        id.toString(),
                        name,
                        startDate,
                        country,
                        network,
                        status)
                it.findNavController().navigate(action)*/

                //it.findNavController().navigate(R.id.detailFragment, bundle)
            //}

        }*/
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

   inner class FavoritesTVShowViewHolder(val binding: ItemContainerTvShowBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bindTVShow(tvShow: TVShow) {
            binding.tvShow = tvShow
            binding.executePendingBindings()
            /*binding.root.setOnClickListener {
                favoritesListener.onTVShowClicked(tvShow)
            }*/
            binding.ivDelete.setOnClickListener {
                favoritesListener.removeTVShowFromFavoritesList(tvShow, adapterPosition)
            }
            binding.ivDelete.visibility = View.VISIBLE

        }


}
}