package com.example.session2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.session2.databinding.FragmentEditInformationBinding
import com.example.session2.model.Profiles
import com.example.session2.viewmodel.AuthViewModel
import com.example.session2.viewmodel.ProfileViewModel
import com.example.session2.viewmodel.StateViewModel
import kotlinx.coroutines.launch


class EditInformationFragment : Fragment() {
    private lateinit var binding: FragmentEditInformationBinding
    private lateinit var stateViewModel: StateViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var authViewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditInformationBinding.inflate(inflater, container, false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]
        profileViewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        var result: Profiles? = null
        lifecycleScope.launch {
            result = profileViewModel.getProfileData()
        }.invokeOnCompletion {
            if (result != null) {
                binding.name.setText(result?.fullname.toString())
                binding.number.setText(result?.phone.toString())
            }
        }
        stateViewModel.setBottomVisible(false)
        stateViewModel.setVisible(false)


        binding.pass2.editText?.doAfterTextChanged {
            if (binding.name.text.toString().length > 5
                && binding.number.text.toString().length > 5
                && binding.pass.editText?.text.toString() == binding.pass2.editText?.text.toString()
                && binding.pass2.editText?.text.toString().length > 6
            ) {
                binding.btnSignUp.isEnabled = true
            }
        }

        binding.btnSignUp.setOnClickListener {
            var result: Profiles? = null
            lifecycleScope.launch {
                result = profileViewModel.setProfileData(
                    Profiles(
                        fullname = binding.name.text.toString(),
                        phone = binding.number.text.toString()
                    )
                )
            }.invokeOnCompletion {
                if (result != null) {
                    Toast.makeText(requireContext(), "Okey", Toast.LENGTH_LONG).show()
                }
            }
        }


            return binding.root
        }
    }

