package com.ida.challengebinar.ui.register

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ida.challengebinar.R
import com.ida.challengebinar.data.room.UserEntity
import com.ida.challengebinar.data.room.UserRepository
import com.ida.challengebinar.databinding.FragmentRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = UserRepository(requireContext())

        binding.btnRegister.setOnClickListener {
            val imageUri: Uri = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + resources.getResourcePackageName(R.drawable.default_profile)
                        + '/' + resources.getResourceTypeName(R.drawable.default_profile) + '/'
                        + resources.getResourceEntryName(R.drawable.default_profile)
            )
            val username = binding.etUsername.text
            val email = binding.etEmail.text
            val password = binding.etPassword.text
            val konfirmasiPassword = binding.etConfirmPassword.text

            when {
                username.isNullOrEmpty() -> {
                    binding.tilUsername.error = getString(R.string.username_belum_diisi)
                }
                email.isNullOrEmpty() -> {
                    binding.tilEmail.error = getString(R.string.email_belum_diisi)
                }
                password.isNullOrEmpty() -> {
                    binding.tilPassword.error = getString(R.string.password_belum_diisi)
                }
                konfirmasiPassword.isNullOrEmpty() -> {
                    binding.tilConfirmPassword.error = getString(R.string.konfirmasi_password_belum_diisi)
                }
                password.toString().lowercase() != konfirmasiPassword.toString().lowercase() -> {
                    binding.tilConfirmPassword.error = getString(R.string.password_tidak_sama)
                }
                else -> {
                    val user = UserEntity(null, username.toString(), email.toString(), password.toString(), imageUri.toString())

                    lifecycleScope.launch(Dispatchers.IO) {
                        val result= repository.addUser(user)
                        runBlocking(Dispatchers.Main) {
                            if (result != 0.toLong()) {
                                Toast.makeText(activity, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                            } else {
                                val snackbar = Snackbar.make(it,"Registrasi gagal, coba lagi nanti!", Snackbar.LENGTH_INDEFINITE)
                                snackbar.setAction("Oke") {
                                    snackbar.dismiss()
                                }
                                snackbar.show()
                            }
                        }
                    }
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}