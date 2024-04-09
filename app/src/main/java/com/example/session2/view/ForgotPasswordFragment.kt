/**
 * Author Korovkina Valentina
 * Created at 09/04/24
 *
 * */
package com.example.session2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentForgotPasswordBinding
import com.example.session2.viewmodel.AuthViewModel
import kotlinx.coroutines.launch


class ForgotPasswordFragment : Fragment() {
   private lateinit var binding: FragmentForgotPasswordBinding
   private lateinit var authmodel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater,container,false)
        authmodel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]



        binding.sendOTP.setOnClickListener {
            try {
                if (binding.textEmail.text.toString().contains("@")
                    && binding.textEmail.text.toString().length > 6){
                    lifecycleScope.launch {
                        authmodel.verifOTP(binding.textEmail.text.toString())
                    }
                }else {
                    Helper.alert(requireContext(),resources.getString(R.string.error),resources.getString(R.string.error_email_mes))
                }
            }catch (e:Exception){
                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
            }
            Navigation.findNavController(binding.root).navigate(R.id.action_forgotPasswordFragment_to_OTPVerFragment)
        }

        return binding.root
    }

}