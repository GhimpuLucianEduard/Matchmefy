package com.lucianghimpu.matchmefy.presentation.matchResult

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_match_result.*
import org.koin.android.viewmodel.ext.android.viewModel


class MatchResultFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultBinding>() {
    private val args: MatchResultFragmentArgs by navArgs()
    override val viewModel: MatchResultViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_match_result
    override fun setViewDataBindingViewModel() {
        binding.viewModel = viewModel
    }

    lateinit var pageViewAdapter: MatchResultPageViewerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initData(args.matchResult)

        pageViewAdapter = MatchResultPageViewerAdapter(this, viewModel.stateManager)
        viewPager.adapter = pageViewAdapter
        viewPager.isUserInputEnabled = false
        dotsIndicator.dotsClickable = false
        dotsIndicator.isClickable
        dotsIndicator.setViewPager2(viewPager)

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

            if (isSpotifyInstalled) {
                val uri = it.peekContent()
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(uri)
                intent.putExtra(
                    Intent.EXTRA_REFERRER,
                    Uri.parse("android-app://" + context!!.packageName)
                )
                startActivity(intent)
            }
        })
    }
}
