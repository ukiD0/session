package com.example.session2.view

import android.content.Intent
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.DbCon
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentSignUpBinding
import com.example.session2.viewmodel.AuthViewModel
import com.example.session2.viewmodel.StateViewModel
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var authmodel: AuthViewModel
    private lateinit var statemodel: StateViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        authmodel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        statemodel = ViewModelProvider(requireActivity())[StateViewModel::class.java]

        statemodel.setVisible(false)
        statemodel.setBottomVisible(false)

        binding.textSignIn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_logInFragment)
        }

        binding.linkforpdf.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_pdfViewFragment)
        }

        binding.google.setOnClickListener {
//            try {
//                lifecycleScope.launch {
//                    authmodel.google()
//                }
//            }catch (e:Exception){
//                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
//            }
            Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_homeFragment)
        }

        binding.checkb.setOnClickListener {
            binding.btnSignUp.isEnabled = binding.checkb.isChecked
        }

        binding.btnSignUp.setOnClickListener {
            if (binding.name.text.toString().length > 2
                && binding.number.text.toString().length > 2
                && binding.email.text.toString().length > 2
                && binding.email.text.toString().contains("@")
                && binding.email.text.toString().contains(".")
                && binding.pass.editText!!.text.toString().equals(binding.pass2.editText!!.text.toString())
                && binding.pass2.editText!!.text.toString() .length > 6
                && binding.checkb.isChecked){
                try {
                    lifecycleScope.launch{
                        authmodel.auth(binding.email.text.toString(),binding.pass2.editText!!.text.toString())
                        try {
                            val user = DbCon.supabase.auth.currentUserOrNull()
                            if (user != null) {
                                Navigation.findNavController(binding.root)
                                    .navigate(R.id.action_signUpFragment_to_homeFragment)
                            }
                        }catch (e:Exception){
                            Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
                        }
                    }
                }catch (e:Exception){
                    Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
                }
            }else{
                Helper.alert(requireContext(),resources.getString(R.string.error),resources.getString(R.string.error_email_mes))
            }
        }


        return binding.root
    }


}