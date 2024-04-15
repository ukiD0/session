package com.example.session2.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.session2.MainActivity
import com.example.session2.R
import com.example.session2.databinding.FragmentPdfViewBinding
import com.example.session2.viewmodel.StateViewModel


class PdfViewFragment : Fragment() {
   private lateinit var binding: FragmentPdfViewBinding
   private lateinit var stateviewmodel: StateViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPdfViewBinding.inflate(inflater,container,false)
        stateviewmodel = ViewModelProvider(requireActivity())[StateViewModel::class.java]

        stateviewmodel.setVisible(true)
        stateviewmodel.setTitle("Пользовательское соглашение")
        stateviewmodel.setArrow(true)

        val backpls = requireActivity().findViewById<ImageView>(R.id.arrow_backkkkk)
        backpls.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_pdfViewFragment_to_signUpFragment)
        }

        binding.pdfff.fromAsset("images.pdf").load()

        return binding.root
    }


}