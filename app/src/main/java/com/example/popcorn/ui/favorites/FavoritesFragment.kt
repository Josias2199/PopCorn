package com.example.popcorn.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.popcorn.common.MyApp
import com.example.popcorn.data.local.entity.TVShow
import com.example.popcorn.databinding.FragmentFavoritesBinding
import com.example.popcorn.utilities.TempDataHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class FavoritesFragment : Fragment(), FavoritesListener {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var adapter: FavoritesListAdapter
    private lateinit var favoritesList: MutableList<TVShow>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        doInitialization()
        return binding.root
    }

    private fun doInitialization() {
        favoritesList = mutableListOf()
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        binding.ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        //favoritesList = mutableListOf()

        loadFavorites()
    }

    private fun loadFavorites() {
        binding.isLoading = true
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(viewModel.loadFavoritesList().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.isLoading = false
                if (favoritesList.size > 0) {
                    favoritesList.clear()
                }
                favoritesList.addAll(it)
                adapter = FavoritesListAdapter(favoritesList, this)
                binding.rvFavorites.adapter = adapter
                binding.rvFavorites.visibility = View.VISIBLE
                compositeDisposable.dispose()
            }
        )
    }

    override fun onResume() {
        super.onResume()
        if (TempDataHolder.IS_FAVORITES_LIST_UPDATE) {
            loadFavorites()
            TempDataHolder.IS_FAVORITES_LIST_UPDATE = false
        }
    }


    override fun removeTVShowFromFavoritesList(tvShow: TVShow, position: Int) {
        val compositeDisposableForDelete = CompositeDisposable()
        compositeDisposableForDelete.add(viewModel.removeTVShowFromFavorites(tvShow)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                favoritesList.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, adapter.itemCount)
                compositeDisposableForDelete.dispose()
            })
    }
}