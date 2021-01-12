package com.example.popcorn.ui.home.detail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.popcorn.R
import com.example.popcorn.data.local.entity.TVShow
import com.example.popcorn.databinding.DetailFragmentBinding
import com.example.popcorn.ui.home.episodes.EpisodesBottomSheetDialog
import com.example.popcorn.utilities.TempDataHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    //private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DetailFragmentBinding

    //private lateinit var tvShow: TVShow
    private val args: DetailFragmentArgs by navArgs()
    private val visible = View.VISIBLE
    private var isTVShowAvailableInFavoritesList: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DetailFragmentBinding.inflate(layoutInflater)
        //return inflater.inflate(R.layout.detail_fragment, container, false)
        doInitialization()
        return binding.root
    }

    private fun checkTVShowInFavoritesList() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(viewModel.getTVShowFromFavoritesList(args.tvShow.id.toString())
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isTVShowAvailableInFavoritesList = true
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                compositeDisposable.dispose()
            })
    }

    private fun doInitialization() {
        //tvShow = arguments?.get("tvShow_") as TVShow
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        binding.ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        checkTVShowInFavoritesList()
        getTVShowDetails()
    }

    private fun getTVShowDetails() {
        with(binding) {
            isLoading = true
            val tvShowId = args.tvShow.id.toString()
            viewModel.getTVShowDetails(tvShowId).observe(viewLifecycleOwner, Observer {
                isLoading = false
                it.tvShow.let { details ->
                    details.pictures.let { list ->
                        loadImageSlider(list)
                    }
                    tvShowImageURL = it.tvShow.image_path
                    description =
                        HtmlCompat.fromHtml(details.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                            .toString()

                    tvReadMore.setOnClickListener {
                        if (tvReadMore.text.toString() == "Read More") {
                            tvDescription.maxLines = Int.MAX_VALUE
                            tvDescription.ellipsize = null
                            tvReadMore.text = getString(R.string.read_less)
                        } else {
                            tvDescription.maxLines = 4
                            tvDescription.ellipsize = TextUtils.TruncateAt.END
                            tvReadMore.text = getString(R.string.read_more)
                        }
                    }

                    rating = String.format(Locale.getDefault(), "%.2f", details.rating.toDouble())
                    if (details.genres != null)
                        genre = details.genres[0]
                    else
                        genre = "N/A"
                    runtime = details.runtime + " Min"

                    btnWebSite.setOnClickListener { v ->
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(details.url)
                        startActivity(intent)
                    }

                    btnEpisodes.setOnClickListener {
                        val episodesBottomSheetDialog: EpisodesBottomSheetDialog =
                            EpisodesBottomSheetDialog()
                        episodesBottomSheetDialog.setData(details.episodes, args.tvShow.name)
                        episodesBottomSheetDialog.show(
                            requireActivity().supportFragmentManager,
                            "add_item"
                        )

                    }

                    ivFavorite.setOnClickListener {
                        val compositeDisposable = CompositeDisposable()
                        if (isTVShowAvailableInFavoritesList)
                            compositeDisposable.add(viewModel.removeTVShowFromFavoritesList(args.tvShow)
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    isTVShowAvailableInFavoritesList = false
                                    TempDataHolder.IS_FAVORITES_LIST_UPDATE = true
                                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite_border)
                                    Toast.makeText(
                                        requireContext(),
                                        "Removed from Favorites List",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    compositeDisposable.dispose()
                                })
                        else {
                            compositeDisposable.add(viewModel.addToWatchlist(args.tvShow)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    TempDataHolder.IS_FAVORITES_LIST_UPDATE = true
                                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                                    Toast.makeText(
                                        requireContext(),
                                        "Added to Favorites",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                    }

                    loadBasicTVShowDetails()
                    setViewVisibility()
                }
            })
        }
    }


    private fun loadImageSlider(sliderImages: List<String>) {
        binding.sliderViewPager.offscreenPageLimit = 1
        binding.sliderViewPager.adapter = ImageSliderAdapter(sliderImages)
        binding.sliderViewPager.visibility = View.VISIBLE
        binding.viewFadingEdge.visibility = View.VISIBLE
        setupSliderIndicators(sliderImages.size)
        binding.sliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(position)
            }
        })
    }

    private fun setupSliderIndicators(count: Int) {
        val indicators = arrayOfNulls<ImageView>(count)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(activity?.applicationContext)
            indicators[i]?.setImageDrawable(
                activity?.applicationContext?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.background_slider_indicator_inactive
                    )
                }
            )
            indicators[i]?.layoutParams = layoutParams
            binding.layoutSliderIndicators.addView(indicators[i])
        }
        binding.layoutSliderIndicators.visibility = View.VISIBLE
        setCurrentSliderIndicator(0)
    }

    private fun setCurrentSliderIndicator(position: Int) {
        val childCount: Int = binding.layoutSliderIndicators.childCount
        var imageView: ImageView?
        for (i in 0..childCount) {
            imageView = binding.layoutSliderIndicators.getChildAt(i) as? ImageView
            if (i == position)
                imageView?.setImageDrawable(activity?.let {
                    ContextCompat.getDrawable(
                        it.applicationContext,
                        R.drawable.background_slider_indicator_active
                    )
                })
            else
                imageView?.setImageDrawable(activity?.let {
                    ContextCompat.getDrawable(
                        it.applicationContext,
                        R.drawable.background_slider_indicator_inactive
                    )
                })
        }
    }


    private fun loadBasicTVShowDetails() {
        with(binding) {
            with(args.tvShow) {
                binding.tvShowName = args.tvShow.name
                networkCountry = "$network ($country)"
                binding.status = args.tvShow.status
                startedDate = startDate
                /*tvName.visibility = visible
                tvNetworkCountry.visibility = visible
                tvStatus.visibility = visible
                tvStarted.visibility = visible*/
            }
        }
    }

    private fun setViewVisibility() {
        with(binding) {
            ivTVShow.visibility = visible

            tvName.visibility = visible
            tvNetworkCountry.visibility = visible
            tvStatus.visibility = visible
            tvStarted.visibility = visible

            tvDescription.visibility = visible
            tvReadMore.visibility = visible

            viewDivider1.visibility = visible
            llMisc.visibility = visible
            viewDivider2.visibility = visible

            btnWebSite.visibility = visible
            btnEpisodes.visibility = visible

            ivFavorite.visibility = visible
        }

    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShow = arguments?.get("tvShow_") as TVShow
    }*/

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        //
    }*/

}