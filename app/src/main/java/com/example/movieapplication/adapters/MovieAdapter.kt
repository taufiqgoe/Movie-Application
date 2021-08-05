package com.example.movieapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.databinding.MainItemLayoutBinding
import com.example.movieapplication.models.MovieAndPos

class MovieAdapter: PagingDataAdapter<MovieAndPos, MovieAdapter.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MainItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder) {
            if (item != null) {
                binding.tvName.text = item.movie.title
            }
        }
    }

    class ViewHolder(_binding: MainItemLayoutBinding) : RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MovieAndPos>() {
            override fun areItemsTheSame(oldItem: MovieAndPos, newItem: MovieAndPos): Boolean = oldItem.movie.id == newItem.movie.id

            override fun areContentsTheSame(oldItem: MovieAndPos, newItem: MovieAndPos): Boolean = oldItem == newItem
        }
    }
}
