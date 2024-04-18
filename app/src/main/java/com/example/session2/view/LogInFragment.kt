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
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.DbCon
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentLogInBinding
import com.example.session2.viewmodel.AuthViewModel
import com.example.session2.viewmodel.StateViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.launch


class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private lateinit var authmodel: AuthViewModel
    private lateinit var stateViewModel: StateViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater,container,false)
        authmodel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]

        stateViewModel.setVisible(false)
        stateViewModel.setBottomVisible(false)

        binding.google.setOnClickListener {
            try {
                lifecycleScope.launch {
                    authmodel.google()
                }
            }catch (e:Exception){
                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
            }
        }
        binding.textSignUp.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_logInFragment_to_signUpFragment)
        }

        binding.textForgot.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_logInFragment_to_forgotPasswordFragment)
        }

        binding.textPass.editText?.doAfterTextChanged {
            try {
                if (binding.textEmail.text.toString().length > 5
                    && binding.textEmail.text.toString().contains("@")
                    && binding.textEmail.text.toString().contains(".")
                    && binding.textPass.editText!!.text.toString().length > 6){
                    binding.btnLogIN.isEnabled = true
                }
            }catch (e:Exception){
                Helper.alert(requireContext(),resources.getString(R.string.error),resources.getString(R.string.error_email_mes))
            }
        }

        binding.btnLogIN.setOnClickListener {
            try {
                binding.progBar.isVisible = true
                binding.mainContaienrr.isVisible = false
                var res: UserInfo? = null
                lifecycleScope.launch {
                    try {
                        res = authmodel.registration(
                            binding.textEmail.text.toString() ,
                            binding.textPass.editText!!.text.toString()
                        )
                    }catch (e:Exception){
                        Helper.alert(requireActivity(),e.cause.toString(),e.message.toString())
                    }

                }.invokeOnCompletion {
                    if (res != null){
                        Navigation.findNavController(binding.root).navigate(R.id.action_logInFragment_to_homeFragment)
                    }else{
                        binding.progBar.isVisible = false
                        binding.mainContaienrr.isVisible = true
                    }
                }
            }catch (e:Exception){
                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
            }
        }


        return binding.root
    }
}