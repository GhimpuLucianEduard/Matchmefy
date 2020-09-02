package com.lucianghimpu.matchmefy.presentation.matchResult

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_match_result.*
import org.koin.android.viewmodel.ext.android.viewModel


class MatchResultFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultBinding>(
    enterTransitionResId = android.R.transition.slide_bottom
) {
    private val args: MatchResultFragmentArgs by navArgs()
    override val viewModel: MatchResultViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_match_result
    override fun setViewDataBindingViewModel() {
        binding.viewModel = viewModel
    }

    private lateinit var pageViewAdapter: MatchResultPageViewerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init vm data
        viewModel.initData(args.matchResult)

        initViewPager()
        initObservers()
    }

    private fun initViewPager() {
        pageViewAdapter = MatchResultPageViewerAdapter(this, viewModel.stateManager)
        viewPager.adapter = pageViewAdapter
        viewPager.isUserInputEnabled = false
        dotsIndicator.dotsClickable = false
        dotsIndicator.isClickable
        dotsIndicator.setViewPager2(viewPager)
    }

    private fun initObservers() {

        viewModel.stateIndex.observe(this@MatchResultFragment, Observer {
            viewPager.currentItem = it
        })

        viewModel.openPlaylistEvent.observe(this@MatchResultFragment, Observer {
            val packageManager = activity!!.packageManager
            val isSpotifyInstalled = try {
                packageManager.getPackageInfo("com.spotify.music", 0)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }

            val playlist = it.peekContent()
            
            if (isSpotifyInstalled) {
                AppAnalytics.trackLog("Spotify app found, open playlist in Spotify")
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(playlist.uri)
                intent.putExtra(
                    Intent.EXTRA_REFERRER,
                    Uri.parse("android-app://" + context!!.packageName)
                )
                startActivity(intent)
            } else {
                AppAnalytics.trackLog("Spotify app not found, open playlist in Browser")
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(playlist.external_urls.spotify)
                )
                startActivity(browserIntent)
            }
        })
    }
}
