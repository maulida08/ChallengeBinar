package com.ida.challengebinar.ui.register

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ida.challengebinar.R
import com.ida.challengebinar.data.room.UserEntity
import com.ida.challengebinar.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by viewModels()

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
                    Toast.makeText(requireContext(), getString(R.string.username_belum_diisi), Toast.LENGTH_SHORT).show()
                }
                email.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), getString(R.string.email_belum_diisi), Toast.LENGTH_SHORT).show()
                }
                password.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), getString(R.string.password_belum_diisi), Toast.LENGTH_SHORT).show()
                }
                konfirmasiPassword.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), getString(R.string.konfirmasi_password_belum_diisi), Toast.LENGTH_SHORT).show()
                }
                password.toString().lowercase() != konfirmasiPassword.toString().lowercase() -> {
                    Toast.makeText(requireContext(), getString(R.string.password_tidak_sama), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val user = UserEntity(null, username.toString(), email.toString(), password.toString(), imageUri.toString())
                    registerViewModel.addUser(user)
                    registerViewModel.register.observe(viewLifecycleOwner) {
                        if (it != 0.toLong()) {
                            Toast.makeText(context, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        } else {
                            val snackbar = Snackbar.make(binding.root,"Registrasi gagal, coba lagi nanti!", Snackbar.LENGTH_INDEFINITE)
                            snackbar.setAction("Oke") {
                                snackbar.dismiss()
                            }
                            snackbar.show()
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