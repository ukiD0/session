/**
 * Author Korovkina Valentina
 * Created at 10/04/24
 *
 * */
package com.example.session2.view

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.decodeFile
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
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentProfileBinding
import com.example.session2.model.Profiles
import com.example.session2.viewmodel.AuthViewModel
import com.example.session2.viewmodel.BucketViewModel
import com.example.session2.viewmodel.ProfileViewModel
import com.example.session2.viewmodel.StateViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var authModel: AuthViewModel
    private lateinit var stateViewModel: StateViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var bucketViewModel: BucketViewModel
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            binding.photo.setImageURI(result.data?.data)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        authModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]
        profileViewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]
        bucketViewModel = ViewModelProvider(requireActivity())[BucketViewModel::class.java]

        stateViewModel.setTitle("Profile")
        stateViewModel.setVisible(true)
        stateViewModel.setBottomVisible(true)




        var image: ByteArray? = null
        lifecycleScope.launch {
            try {
               image = bucketViewModel.getImage()
            }catch (e:Exception){
                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
            }
        }.invokeOnCompletion {
            if (image != null){
                binding.photo.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image!!.size))
            }
                binding.mainContainer.isVisible = true
                binding.progBar.isVisible = false
        }

        binding.photo.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
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
        binding.aboutus.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_yandexMapFragment2)
        }

            var name: Profiles? = null
            lifecycleScope.launch {
            name = profileViewModel.getProfileData()
            }.invokeOnCompletion {
            if (name != null){
                binding.textHello.setText("Hello " + name?.fullname.toString().split(" ")[0] )
            }
            }
            var money: Profiles? = null
            lifecycleScope.launch{
                money = profileViewModel.getProfileData()
            }.invokeOnCompletion {
                if(money != null){
                    binding.moneyText.setText("N" + money?.balance.toString())
                }
            }



        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        binding.photo.setImageURI(data?.data)
        var inputStream : InputStream? = null
        binding.progBar.isVisible = true
        binding.mainContainer.isVisible = false
        try {
            inputStream = requireActivity().contentResolver.openInputStream(data?.data!!)
            val bitMap = BitmapFactory.decodeStream(inputStream)
            val outPutStream = ByteArrayOutputStream()
            bitMap.compress(Bitmap.CompressFormat.JPEG,100,outPutStream)
            lifecycleScope.launch{
                try {
                    bucketViewModel.uploadImage(outPutStream.toByteArray())
                }catch (e:Exception){
                    Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
                }
            }.invokeOnCompletion {
                binding.progBar.isVisible = false
                binding.mainContainer.isVisible = true
            }
        }catch (e:Exception){
            Log.e("error",e.cause.toString())
        }finally {
            inputStream?.close()
        }

    }
}