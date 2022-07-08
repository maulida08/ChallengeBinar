package com.ida.challengebinar.ui.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ida.challengebinar.R
import com.ida.challengebinar.data.room.UserEntity
import com.ida.challengebinar.data.service.Status
import com.ida.challengebinar.databinding.FragmentHomeBinding
import com.ida.challengebinar.room.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getDataStore()
        homeViewModel.userDataStore.observe(viewLifecycleOwner) {
            binding!!.tvName.text = it.username
        }

        fetchAllPopularMovie()
        updateUser()
    }

    private fun fetchAllPopularMovie() {
        homeViewModel.popularMovie.observe(viewLifecycleOwner) {
            when(it.status){
                Status.LOADING -> {
                    binding!!.rvListMovie.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding!!.rvListMovie.visibility = View.VISIBLE
                    showListPopularMovie(it.data?.results)
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        homeViewModel.getPopularMovie()
    }

    private fun showListPopularMovie(data: List<Result>?) {
        val adapter = PopularAdapter(object : PopularAdapter.OnClickListener {
            override fun onClickItem(data: Result) {
                val homeToDetail = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(data.id)
                findNavController().navigate(homeToDetail)
            }
        })

        adapter.submitData(data)
        binding?.rvListMovie?.adapter = adapter
    }

    private fun updateUser(){
        binding?.btnProfle?.setOnClickListener {
            homeViewModel.getUser(binding?.tvName?.text.toString())
            homeViewModel.getDataUser.observe(viewLifecycleOwner) {
                if (it != null) {
                    val user = UserEntity(
                        it.id,
                        it.username,
                        it.email,
                        it.password,
                        it.uri
                    )
                    val navigateUpdate = HomeFragmentDirections.actionHomeFragmentToProfileFragment(user)
                    findNavController().navigate(navigateUpdate)
                }
            }
        }
        binding!!.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dialog.dismiss()
                    homeViewModel.deleteUserFromPref()
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                }
                .setNegativeButton("Batal") {dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchAllPopularMovie()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}