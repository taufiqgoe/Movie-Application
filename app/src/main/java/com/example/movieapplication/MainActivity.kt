package com.example.movieapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapplication.adapters.MovieAdapter
import com.example.movieapplication.databinding.ActivityMainBinding
import com.example.movieapplication.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MovieAdapter()

        binding.apply {
            rvMain.layoutManager = LinearLayoutManager(binding.root.context)
            rvMain.adapter = adapter
        }

        viewModel.getPopularMovie(1)
        viewModel.myResponse.observe(this, {
            if (it.isSuccessful) {
                adapter.submitList(it.body()!!.models)
            } else {
                Toast.makeText(this, "Failed to load movies", Toast.LENGTH_SHORT).show()
            }
        })
    }
}