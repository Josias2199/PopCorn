package com.example.popcorn.ui.home.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.databinding.ItemContainerEpisodeBinding
import com.example.popcorn.data.local.entity.Episode

class EpisodesAdapter(private val episodes: List<Episode>): RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: ItemContainerEpisodeBinding = DataBindingUtil.inflate(
            layoutInflater!!, R.layout.item_container_episode, parent, false
        )
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bindEpisode(episodes[position])
    }

    override fun getItemCount(): Int = episodes.size

    class EpisodeViewHolder(private val binding: ItemContainerEpisodeBinding):
        RecyclerView.ViewHolder(
            binding.root
        ){
        fun bindEpisode(episode: Episode){
            var title: String = "S"
            var season: String = episode.season
            if (season.length == 1) {
                season = "0 $season"
            }
            var episodeNumber = episode.episode
            if (episodeNumber.length == 1) {
                episodeNumber = "0 $episodeNumber"
            }
            episodeNumber = "E $episodeNumber"
            title = "$title $season $episodeNumber"
            binding.title = title
            binding.name = episode.name
            binding.airDate = episode.airDate

        }
    }
}