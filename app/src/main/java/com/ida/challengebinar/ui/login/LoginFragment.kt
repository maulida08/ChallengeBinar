package com.ida.challengebinar.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ida.challengebinar.R
import com.ida.challengebinar.data.UserDataStoreManager
import com.ida.challengebinar.data.room.UserRepository
import com.ida.challengebinar.databinding.FragmentLoginBinding
import com.ida.challengebinar.viewmodel.MainViewModel
import com.ida.challengebinar.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: UserRepository
    private lateinit var viewModel: MainViewModel
    private lateinit var pref: UserDataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = UserRepository(requireContext())

        pref = UserDataStoreManager(requireContext())
        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory(pref))[MainViewModel::class.java]

        viewModel.getDataStore().observe(viewLifecycleOwner) {
            if (it.toString() != UserDataStoreManager.DEFAULT_USERNAME) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }

        binding.btnRegisterText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val etUsername = binding.etUsername.text
            val etPassword = binding.etPassword.text

            when {
                etUsername.isNullOrEmpty() -> {
                    binding.tilUsername.error = getString(R.string.username_belum_diisi)
                }
                etPassword.isNullOrEmpty() -> {
                    binding.tilPassword.error = getString(R.string.password_belum_diisi)
                }
                else -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val result = repository.checkUser(etUsername.toString(), etPassword.toString())

                        runBlocking(Dispatchers.Main) {
                            if (result == false) {
                                val snackbar = Snackbar.make(it,"Login gagal, coba periksa email atau password anda", Snackbar.LENGTH_INDEFINITE)
                                snackbar.setAction("Oke") {
                                    snackbar.dismiss()
                                }
                                snackbar.show()
                            } else {
                                Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            }
                        }
                        if (result != false){
                            viewModel.saveDataStore(etUsername.toString())
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}