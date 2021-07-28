package com.example.movieapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapplication.R
import com.example.movieapplication.adapters.viewPager.ViewPagerAdapter
import com.example.movieapplication.databinding.FragmentMovieBinding
import com.example.movieapplication.fragments.moviepage.PopularFragment
import com.example.movieapplication.fragments.moviepage.TopRatedFragment
import com.example.movieapplication.fragments.moviepage.UpcomingFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf(
            PopularFragment(), TopRatedFragment(), UpcomingFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList, childFragmentManager, lifecycle
        )

        binding.vpMovie.adapter = adapter

        TabLayoutMediator(binding.tlMovie, binding.vpMovie) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Popular"
//                    tab.icon = drawable1
                }
                1 -> {
                    tab.text = "Top Rated"
//                    tab.icon = drawable2
                }
                2 -> {
                    tab.text = "Upcoming"
//                    tab.icon = drawable2
                }
            }
        }.attach()

        return binding.root
    }

}