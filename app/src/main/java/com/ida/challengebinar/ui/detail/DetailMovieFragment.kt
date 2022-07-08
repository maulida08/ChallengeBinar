package com.ida.challengebinar.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ida.challengebinar.R
import com.ida.challengebinar.data.service.Status
import com.ida.challengebinar.databinding.FragmentDetailMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailMovieFragmentArgs by navArgs()

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
        detailViewModel.getAllDetail(idMovie)
        detailViewModel.detailMovie.observe(viewLifecycleOwner) {
            when(it.status){
                Status.LOADING -> {
                    binding.tvDetail.visibility = View.GONE
                    binding.tvTaglineValue.visibility = View.GONE
                    binding.tvTitle.visibility = View.GONE
                    binding.tvTagline.visibility = View.GONE
                    binding.tvInputDuration.visibility = View.GONE
                    binding.tvMinutes.visibility = View.GONE
                    binding.tvDescription.visibility = View.GONE
                    binding.tvDate.visibility = View.GONE
                    binding.tvInputDate.visibility = View.GONE
                    binding.tvDuration.visibility = View.GONE
                    binding.ivImage.visibility = View.GONE
                    binding.ivPoster.visibility = View.GONE
                    binding.llDetail.visibility = View.GONE
                    binding.cvPoster.visibility = View.GONE
                    binding.btnBack.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.tvDetail.visibility = View.VISIBLE
                    binding.tvTaglineValue.visibility = View.VISIBLE
                    binding.tvTitle.visibility = View.VISIBLE
                    binding.tvTagline.visibility = View.VISIBLE
                    binding.tvInputDuration.visibility = View.VISIBLE
                    binding.tvMinutes.visibility = View.VISIBLE
                    binding.tvDescription.visibility = View.VISIBLE
                    binding.tvDate.visibility = View.VISIBLE
                    binding.tvInputDate.visibility = View.VISIBLE
                    binding.tvDuration.visibility = View.VISIBLE
                    binding.ivImage.visibility = View.VISIBLE
                    binding.ivPoster.visibility = View.VISIBLE
                    binding.llDetail.visibility = View.VISIBLE
                    binding.cvPoster.visibility = View.VISIBLE
                    binding.btnBack.visibility = View.VISIBLE

                    val image = it.data?.backdropPath
                    if (image == null) {
                        Glide.with(binding.root
                        ).load(R.drawable.default_image)
                            .into(binding.ivImage)
                    } else {
                        Glide.with(binding.root
                        ).load(imageBase + image)
                            .into(binding.ivImage)
                    }

                    val poster = it.data?.posterPath
                    if (poster == null) {
                        Glide.with(binding.root
                        ).load(R.drawable.ic_baseline_image)
                            .into(binding.ivPoster)
                    } else {
                        Glide.with(binding.root
                        ).load(imageBase + image)
                            .into(binding.ivPoster)
                    }

                    binding.tvTitle.text = it.data?.title
                    binding.tvTitle.isSelected = true

                    binding.tvInputDate.text = it.data?.releaseDate

                    binding.tvInputDuration.text = it.data?.runtime.toString()

                    binding.tvDetail.text = it.data?.overview

                    val tagLine = it.data?.tagline
                    if (tagLine == "") {
                        binding.tvTagline.visibility = View.GONE
                        binding.tvTaglineValue.visibility = View.GONE
                    } else {
                        binding.tvTaglineValue.text = it.data?.tagline
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
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

