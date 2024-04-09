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
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentLogInBinding


class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater,container,false)

        binding.textForgot.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_logInFragment_to_forgotPasswordFragment)
        }

        binding.textPass.doAfterTextChanged {
            try {
                if (binding.textEmail.text.toString().length > 5
                    && binding.textEmail.text.toString().contains("@")
                    && binding.textEmail.text.toString().contains(".")
                    && binding.textPass.text.toString().length > 6){
                    binding.btnLogIN.isEnabled = true
                }
            }catch (e:Exception){
                Helper.alert(requireContext(),resources.getString(R.string.error),resources.getString(R.string.error_email_mes))
            }
        }

        binding.btnLogIN.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_logInFragment_to_homeFragment)
        }


        return binding.root
    }

}