package com.ida.challengebinar.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ida.challengebinar.R
import com.ida.challengebinar.databinding.FragmentDetailMovieBinding
import com.ida.challengebinar.viewmodel.DetailViewModel

class DetailMovieFragment : Fragment() {
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val args: DetailMovieFragmentArgs by navArgs()
    private lateinit var viewModel: DetailViewModel

    private val imageBase = "https://image.tmdb.org/t/p/w500/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idMovie = args.id
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.getAllDetail(idMovie)
        viewModel.detailMovie.observe(viewLifecycleOwner){

            val image = it.backdropPath
            if (image == null) {
                Glide.with(binding.root
                ).load(R.drawable.default_image)
                    .into(binding.ivImage)
            } else {
                Glide.with(binding.root
                ).load(imageBase + image)
                    .into(binding.ivImage)
            }

            val poster = it.posterPath
            if (poster == null) {
                Glide.with(binding.root
                ).load(R.drawable.ic_baseline_image)
                    .into(binding.ivPoster)
            } else {
                Glide.with(binding.root
                ).load(imageBase + image)
                    .into(binding.ivPoster)
            }

            binding.tvTitle.text = it.title
            binding.tvTitle.isSelected = true

            binding.tvInputDate.text = it.releaseDate

            binding.tvInputDuration.text = it.runtime.toString()

            binding.tvDetail.text = it.overview

            val tagLine = it.tagline
            if (tagLine == "") {
                binding.tvTagline.visibility = View.GONE
                binding.tvTaglineValue.visibility = View.GONE
            } else {
                binding.tvTaglineValue.text = it.tagline
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailMovieFragment_to_homeFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}