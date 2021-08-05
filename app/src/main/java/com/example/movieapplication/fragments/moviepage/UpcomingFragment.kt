package com.example.movieapplication.fragments.moviepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapplication.R
import com.example.movieapplication.adapters.MovieAdapter
import com.example.movieapplication.databinding.FragmentPopularBinding
import com.example.movieapplication.databinding.FragmentUpcomingBinding
import com.example.movieapplication.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        val adapter = MovieAdapter()

        viewModel.getUpcomingFromDb()
        viewModel.upcoming.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.submitData(lifecycle, it)
            } else {
                Toast.makeText(binding.root.context, "List are empty", Toast.LENGTH_SHORT).show()
            }
        })

        binding.apply {
            rvUpcomingMovie.layoutManager = LinearLayoutManager(root.context)
            rvUpcomingMovie.adapter = adapter
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}