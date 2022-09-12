package com.ddevs.edgeify.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ddevs.edgeify.R
import com.ddevs.edgeify.databinding.FragmentGetImageBinding
import java.io.File


class GetImageFragment : Fragment() {
    lateinit var binding:FragmentGetImageBinding
    val imagePic = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
        Log.e("Camerapic",it.toString());
    }
    val captureImage = registerForActivityResult(ActivityResultContracts.TakePicture()){
        Log.e("Camera",it.toString());
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_get_image, container, false)
        binding.pickBtn.setOnClickListener {
            imagePic.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.captureBtn.setOnClickListener {
            val file = File(requireContext().filesDir, "images/image.jpeg")
            val uri: Uri = FileProvider.getUriForFile(
                requireContext(),
                requireActivity().applicationContext.packageName + ".provider",
                file
            )
            captureImage.launch(uri)
        }
        return binding.root
    }
}