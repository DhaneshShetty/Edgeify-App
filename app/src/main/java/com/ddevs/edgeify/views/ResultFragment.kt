package com.ddevs.edgeify.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ddevs.edgeify.MainViewModel
import com.ddevs.edgeify.R
import com.ddevs.edgeify.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    val viewModel:MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_result, container, false)
        viewModel.resultUri.observe(viewLifecycleOwner) {
            if (it != null) {
                Glide.with(requireContext()).load(it.first).into(binding.imageView)
                Glide.with(requireContext()).load(it.second).into(binding.imageView2)
                binding.url.text = it.second
            }
        }
        return binding.root
    }
}