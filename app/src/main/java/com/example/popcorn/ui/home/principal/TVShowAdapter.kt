package com.example.popcorn.ui.home.principal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.databinding.ItemContainerTvShowBinding
import com.example.popcorn.data.local.entity.TVShow
import com.example.popcorn.ui.home.principal.TVShowAdapter.TVShowViewHolder
import com.example.popcorn.ui.search.SearchFragmentDirections
import com.example.popcorn.utilities.TempDataHolder
import java.io.Serializable

class TVShowAdapter(private val tvShows: List<TVShow>) : RecyclerView.Adapter<TVShowViewHolder>() {
    private var layoutInflater: LayoutInflater? = null
    //private lateinit var binding: ItemContainerTvShowBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: ItemContainerTvShowBinding = DataBindingUtil.inflate(
            layoutInflater!!, R.layout.item_container_tv_show, parent, false
        )

        return TVShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        holder.bindTVShow(tvShows[position])

        holder.itemView.setOnClickListener {
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
            if(TempDataHolder.MOVE_FRAGMENT){
                val action = PrincipalFragmentDirections.actionNavigationHomeToDetailFragment(tvShows[position])
                it.findNavController().navigate(action)
                TempDataHolder.MOVE_FRAGMENT = false
            }else {
                val action =
                    SearchFragmentDirections.actionNavigationSearchToDetailFragment(tvShows[position])
                it.findNavController().navigate(action)
                //TempDataHolder.MOVE_FRAGMENT = false
            }
                //it.findNavController().navigate(R.id.detailFragment, bundle)
            //}
        }
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    class TVShowViewHolder(private val binding: ItemContainerTvShowBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bindTVShow(tvShow: TVShow?) {
            binding.tvShow = tvShow
            binding.executePendingBindings()
        }
    }
}