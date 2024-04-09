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
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentNewPasswordBinding


class NewPasswordFragment : Fragment() {

    private lateinit var binding: FragmentNewPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPasswordBinding.inflate(inflater,container,false)

        binding.btnLogIn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_newPasswordFragment_to_homeFragment)
        }

        binding.textPass2.setOnClickListener {
            try {
                if (binding.textPass.text.toString().equals(binding.textPass2.text.toString())
                    && binding.textPass2.text.toString().length > 6){
                    binding.btnLogIn.isEnabled = true
                }
            }catch (e:Exception){
                Helper.alert(requireContext(),resources.getString(R.string.error),resources.getString(R.string.error_email_mes))
            }
        }

        return binding.root
    }

}