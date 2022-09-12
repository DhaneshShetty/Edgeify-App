package com.ddevs.edgeify.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ddevs.edgeify.MainViewModel
import com.ddevs.edgeify.R
import com.ddevs.edgeify.databinding.FragmentGetImageBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class GetImageFragment : Fragment() {
    lateinit var binding:FragmentGetImageBinding
    val viewModel:MainViewModel by lazy{
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    val imagePic = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
        if (it != null) {
            var bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,it);
            viewModel.setBitmap(bitmap)
            viewModel.setUri(it)
        }
        Log.d("Uri",it.toString())
        findNavController().navigate(R.id.action_getImageFragment_to_previewImageFragment)
    }
    val captureImage = registerForActivityResult(ActivityResultContracts.TakePicture()){
        viewModel.setUri(mainUri)
        var bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,mainUri);
        viewModel.setBitmap(bitmap)
        Log.d("Uri",mainUri.toString())
        findNavController().navigate(R.id.action_getImageFragment_to_previewImageFragment)
    }
    lateinit var mainUri:Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_get_image, container, false)
        binding.pickBtn.setOnClickListener {
            imagePic.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.captureBtn.setOnClickListener {
            val getImage: File? = requireActivity().externalCacheDir
            if (getImage != null) {
                mainUri = FileProvider.getUriForFile(requireContext(),requireActivity().applicationContext.packageName + ".provider",File(getImage.path, "profile.png"))
            }
            captureImage.launch(mainUri)
        }
        return binding.root
    }
}