package com.example.popcorn.ui.home.episodes

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popcorn.databinding.FragmentEpisodesBottomSheetBinding
import com.example.popcorn.data.local.entity.Episode

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

class EpisodesBottomSheetDialog() : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEpisodesBottomSheetBinding
    private lateinit var adapter: EpisodesAdapter
    private lateinit var episodes: List<Episode>
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodesBottomSheetBinding.inflate(layoutInflater)
        doInitialization()
        return binding.root
    }

    fun setData(episodes: List<Episode>, name: String) {
        this.episodes = episodes
        this.name = name
    }

    private fun doInitialization(){
        binding.rvEpisodes.setHasFixedSize(true)
        adapter = EpisodesAdapter(episodes)
        binding.rvEpisodes.adapter = adapter
        "Episodes |  $name".also { binding.txtTitle.text = it }
        binding.ivClose.setOnClickListener { dismiss() }

    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.findViewById<RecyclerView>(R.id.list)?.layoutManager =
            LinearLayoutManager(context)
        activity?.findViewById<RecyclerView>(R.id.list)?.adapter =
            arguments?.getInt(ARG_ITEM_COUNT)?.let { ItemAdapter(it) }
    }

    private inner class ViewHolder internal constructor(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.item_container_episode,
            parent,
            false
        )
    ) {

        internal val text: TextView = itemView.findViewById(R.id.text)
    }

    private inner class ItemAdapter internal constructor(private val mItemCount: Int) :
        RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = position.toString()
        }

        override fun getItemCount(): Int {
            return mItemCount
        }
    }

    companion object {

        // TODO: Customize parameters
        fun newInstance(itemCount: Int): ItemListDialogFragment =
            ItemListDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }

    }*/
}