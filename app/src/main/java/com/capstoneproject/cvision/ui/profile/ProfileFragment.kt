package com.capstoneproject.cvision.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.databinding.FragmentProfileBinding
import com.capstoneproject.cvision.utils.Result
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var username: String = ""

    private val profileVM by viewModels<ProfileViewModel> {
        ProfileViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logout.setOnClickListener {
            showMessageConfirm()
        }

        binding.credits.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_creditsActivity)
        }

        binding.about.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_aboutActivity)
        }


        showNameUser()
    }

    private fun showNameUser() {
        profileVM.getName().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.tvNameUser.text = it
            }
        }

        profileVM.getUsername().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.tvUsername.text = it
                username = it
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showMessageConfirm() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.title_dialog_logout))
            .setBackground(resources.getDrawable(R.drawable.background_dialog_confirm))
            .setMessage(resources.getString(R.string.message_dialog_logout))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.logout)) { dialog, _ ->

                if (username.isNotEmpty()) {
                    profileVM.invalidAuth(username).observe(viewLifecycleOwner) {
                        if (it != null) {
                            when (it) {
                                is Result.Success -> {
                                    binding.lottieLoading.visibility = View.GONE
                                    //masih blm optimal
                                    if (!it.data.error) {
                                        profileVM.clearSessionUser()
                                        dialog.dismiss()
                                        Toast.makeText(context, it.data.message, Toast.LENGTH_SHORT)
                                            .show()
                                    } else {
                                        Toast.makeText(context, it.data.message, Toast.LENGTH_SHORT)
                                            .show()
                                    }

                                }

                                is Result.Loading -> {
                                    binding.lottieLoading.visibility = View.VISIBLE
                                    Log.d("RESPONSE AUTH", "load")
                                }

                                is Result.Error -> {
                                    binding.lottieLoading.visibility = View.GONE
                                    Log.d("RESPONSE AUTH", it.error)
                                }
                            }
                        }
                    }
                }

            }
            .show()
    }

}