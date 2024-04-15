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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentNewPasswordBinding
import com.example.session2.viewmodel.AuthViewModel
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.launch


class NewPasswordFragment : Fragment() {

    private lateinit var binding: FragmentNewPasswordBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPasswordBinding.inflate(inflater,container,false)
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        binding.btnLogIn.setOnClickListener {
            var modifUser: UserInfo? = null
            try {
                lifecycleScope.launch {
                  modifUser =  authViewModel.modifUser(binding.textPass2.editText?.text.toString())
                }.invokeOnCompletion {
                    if (modifUser != null){
                        Navigation.findNavController(binding.root).navigate(R.id.action_newPasswordFragment_to_homeFragment)
                    }
                }
            }catch (e:Exception){
                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
            }
        }

        binding.textPass2.editText?.doAfterTextChanged {
            try {
                if (binding.textPass.editText?.text.toString().equals(binding.textPass2.editText?.text.toString())
                    && binding.textPass2.editText?.text.toString().length > 6){
                    binding.btnLogIn.isEnabled = true
                }
            }catch (e:Exception){
                Helper.alert(requireContext(),resources.getString(R.string.error),resources.getString(R.string.error_email_mes))
            }
        }

        return binding.root
    }

}