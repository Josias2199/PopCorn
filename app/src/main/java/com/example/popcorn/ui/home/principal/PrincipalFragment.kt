package com.example.popcorn.ui.home.principal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.databinding.FragmentHomeBinding
import com.example.popcorn.data.local.entity.TVShow
import com.example.popcorn.utilities.TempDataHolder


class PrincipalFragment : Fragment() {

    private lateinit var viewModel: PrincipalViewModel
    private lateinit var binding: FragmentHomeBinding
    private val tvShows: ArrayList<TVShow> = ArrayList()
    private lateinit var adapter: TVShowAdapter
    private var aux: Int = 0
    private var currentPage: Int = 1
    private var totalAvailablePages: Int = 1

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //viewModel = ViewModelProvider(this).get(PrincipalViewModel::class.java)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        doInitialization()

        return binding.root
    }

    private fun doInitialization(){
        TempDataHolder.MOVE_FRAGMENT = true
        binding.rvTvShows.setHasFixedSize(true)
        viewModel = ViewModelProvider(this).get(PrincipalViewModel::class.java)
        adapter = TVShowAdapter(tvShows)
        binding.rvTvShows.adapter = adapter
        binding.rvTvShows.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!binding.rvTvShows.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePages){
                        currentPage += 1
                        getMostPopularTVShows()
                    }
                }
            }
        })
        getMostPopularTVShows()
    }

    private fun getMostPopularTVShows(){
        if (aux != currentPage) {
            toggleLoading()
            viewModel.getMostPopularTVShows(currentPage).observe(viewLifecycleOwner, Observer {
                toggleLoading()
                if (it != null) {
                    totalAvailablePages = it.pages
                    if (it.TVShows != null) {
                        val oldCount: Int = tvShows.size
                        tvShows.addAll(it.TVShows)
                        adapter.notifyItemRangeInserted(oldCount, tvShows.size)
                        aux = currentPage
                    }
                }
            })
        }
    }

    private fun toggleLoading(){
        if(currentPage == 1)
            binding.isLoading = !(binding.isLoading != null && binding.isLoading == true)
        else
            binding.isLoadingMore = !(binding.isLoadingMore != null && binding.isLoadingMore == true)
    }
}