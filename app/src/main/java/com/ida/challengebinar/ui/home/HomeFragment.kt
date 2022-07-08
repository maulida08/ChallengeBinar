package com.ida.challengebinar.ui.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ida.challengebinar.R
import com.ida.challengebinar.data.UserDataStoreManager
import com.ida.challengebinar.data.room.UserEntity
import com.ida.challengebinar.data.room.UserRepository
import com.ida.challengebinar.databinding.FragmentHomeBinding
import com.ida.challengebinar.room.Result
import com.ida.challengebinar.viewmodel.HomeViewModel
import com.ida.challengebinar.viewmodel.MainViewModel
import com.ida.challengebinar.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var repository: UserRepository
    private lateinit var viewModel: HomeViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var pref: UserDataStoreManager

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
        repository = UserRepository(requireContext())

        pref = UserDataStoreManager(requireContext())
        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(pref))[MainViewModel::class.java]

        mainViewModel.getDataStore().observe(viewLifecycleOwner) {
            binding!!.tvName.text = it
        }

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        fetchAllPopularMovie()
        updateUser()
    }

    private fun fetchAllPopularMovie() {
        viewModel.getPopularMovie()
        viewModel.popularMovie.observe(viewLifecycleOwner) {
            showListPopularMovie(it.results)
        }
    }

    private fun showListPopularMovie(data: List<Result>?) {
        val adapter = PopularAdapter(object : PopularAdapter.OnClickListener {
            override fun onClickItem(data: Result) {
                val homeToDetail = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(data.id)
                findNavController().navigate(homeToDetail)
            }
        })

        adapter.submitData(data)
        binding!!.rvListMovie.adapter = adapter
    }

    private fun updateUser(){
        binding!!.btnProfle.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val dataUser = repository.getUser(binding!!.tvName.text.toString())
                runBlocking(Dispatchers.Main) {
                    if (dataUser != null) {
                        val user = UserEntity(
                            dataUser.id,
                            dataUser.username,
                            dataUser.email,
                            dataUser.password,
                            dataUser.uri
                        )
                        val navigateUpdate = HomeFragmentDirections.actionHomeFragmentToProfileFragment(user)
                        findNavController().navigate(navigateUpdate)
                    }
                }
            }
        }
        binding!!.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dialog.dismiss()
                    lifecycleScope.launch(Dispatchers.IO) {
                        pref.deleteUserFromPref()
                    }
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