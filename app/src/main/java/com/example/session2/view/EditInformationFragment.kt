package com.example.session2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentEditInformationBinding
import com.example.session2.viewmodel.AuthViewModel
import com.example.session2.viewmodel.ProfileViewModel
import com.example.session2.viewmodel.StateViewModel
import io.github.jan.supabase.gotrue.user.UserInfo
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
        binding = FragmentEditInformationBinding.inflate(inflater,container,false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]
        profileViewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        stateViewModel.setBottomVisible(false)
        stateViewModel.setVisible(false)

        var phone = ""

        authViewModel.currentPhone.observe(viewLifecycleOwner){
            if (it != null){
                phone = it
            }
        }

        binding.pass2.editText?.doAfterTextChanged {
            if (binding.name.text.toString().length > 5
                && binding.number.text.toString().length > 5
                && binding.pass.editText?.text.toString() == binding.pass2.editText?.text.toString()
                && binding.pass2.editText?.text.toString().length > 6){
                binding.btnSignUp.isEnabled = true
            }
        }

        binding.btnSignUp.setOnClickListener {
            var modifUser: UserInfo? = null
            try {
                lifecycleScope.launch {
                    modifUser = authViewModel.editProfile(binding.number.text.toString())
                }.invokeOnCompletion {
                    Navigation.findNavController(binding.root).navigate(R.id.action_editInformationFragment_to_profileFragment)
                }
            }catch (e:Exception){
                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
            }
        }

        return binding.root
    }

}