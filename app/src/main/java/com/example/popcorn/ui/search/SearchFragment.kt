package com.example.popcorn.ui.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.example.popcorn.data.local.entity.TVShow
import com.example.popcorn.databinding.FragmentDashboardBinding
import com.example.popcorn.ui.home.principal.TVShowAdapter
import com.example.popcorn.utilities.TempDataHolder
import java.util.*

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: SearchViewModel
    private val tvShows: MutableList<TVShow> = mutableListOf()
    private lateinit var adapter: TVShowAdapter
    private var currentPage: Int = 1
    private var totalAvailablePages: Int = 1
    private var timer: Timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDashboardBinding.inflate(layoutInflater)
        //val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        doInitialization()
        return binding.root
    }

    private fun doInitialization() {
        TempDataHolder.MOVE_FRAGMENT = false
        binding.ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        binding.rvTVShows.setHasFixedSize(true)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        adapter = TVShowAdapter(tvShows)
        binding.rvTVShows.adapter = adapter
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSecuence: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(charSecuence: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (timer != null) {
                    timer.cancel()
                }
            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().trim().isNotEmpty()) {
                    timer = Timer()
                    //timer?.let {
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            val handler = Handler(Looper.getMainLooper())
                            handler.post {
                                currentPage = 1
                                totalAvailablePages = 1
                                searchTVShow(editable.toString())
                            }
                        }

                    }, 800)
                    //}
                } else {
                    tvShows.clear()
                    adapter.notifyDataSetChanged()
                }
            }

        })
        binding.rvTVShows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.rvTVShows.canScrollVertically(1)) {
                    if (binding.etSearch.text.toString().isNotEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage += 1
                            searchTVShow(binding.etSearch.text.toString())
                        }
                    }
                }
            }
        })
        binding.etSearch.requestFocus()
    }

    private fun searchTVShow(query: String) {
        toggleLoading()
        viewModel.searchTVShow(query, currentPage).observe(this, {
            toggleLoading()
            if (it != null) {
                totalAvailablePages = it.pages
                if(it.TVShows != null) {
                    val oldCount = tvShows.size
                    tvShows.addAll(it.TVShows)
                    adapter.notifyItemRangeInserted(oldCount, tvShows.size)
                }
            }
        })
    }

    private fun toggleLoading() {
        if (currentPage == 1)
            binding.isLoading = !(binding.isLoading != null && binding.isLoading == true)
        else
            binding.isLoadingMore =
                !(binding.isLoadingMore != null && binding.isLoadingMore == true)
    }


}