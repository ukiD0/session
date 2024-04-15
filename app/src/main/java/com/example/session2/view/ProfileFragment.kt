/**
 * Author Korovkina Valentina
 * Created at 10/04/24
 *
 * */
package com.example.session2.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.databinding.FragmentProfileBinding
import com.example.session2.viewmodel.AuthViewModel
import com.example.session2.viewmodel.ProfileViewModel
import com.example.session2.viewmodel.StateViewModel
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var authModel: AuthViewModel
    private lateinit var stateViewModel: StateViewModel
    private lateinit var profileViewModel: ProfileViewModel

//    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
//        binding.photo.setImageURI(it)
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        authModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]
        profileViewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]

        stateViewModel.setTitle("Profile")
        stateViewModel.setVisible(true)

        binding.photo.setOnClickListener {
//            val chooser = Intent.createChooser(galleryIntent, "Some text here")
//            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
//            startActivityForResult(chooser, requestCode)
//            galleryLauncher.launch("image/*")
        }

        binding.logout.setOnClickListener {
            lifecycleScope.launch {
                authModel.logotttt()
            }
            Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_logInFragment)
        }

        binding.profile.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_editInformationFragment)
        }

        binding.swyyytch.setOnClickListener {
            if (binding.swyyytch.isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
//        binding.textHello.text = "Hello" + profileViewModel.fullname

        return binding.root
    }

}