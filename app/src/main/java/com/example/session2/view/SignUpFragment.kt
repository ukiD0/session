package com.example.session2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentSignUpBinding
import com.example.session2.viewmodel.AuthViewModel


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var authmodel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        authmodel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        binding.textSignIn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_logInFragment)
        }
//        binding.google.setOnClickListener {
//
//        }
        binding.checkb.setOnClickListener {
            if (binding.name.text.toString().length > 2
                && binding.number.text.toString().length > 10
                && binding.email.text.toString().length > 5
                && binding.email.text.toString().contains("@")
                && binding.email.text.toString().contains(".")
                && binding.pass.text.toString().equals(binding.pass2.text.toString())
                && binding.pass2.text.toString().length > 6){
                binding.btnSignUp.isEnabled = true
            }else{
                Helper.alert(requireActivity(),resources.getString(R.string.error),resources.getString(R.string.error_email_mes))
            }
        }
        binding.btnSignUp.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_homeFragment)
        }

        return binding.root
    }

}