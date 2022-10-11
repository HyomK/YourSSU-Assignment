package com.assignment2.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.assignment2.Assignment2
import com.assignment2.R
import com.assignment2.databinding.FragmentEmailBinding
import com.assignment2.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding : FragmentResultBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setClickListener()
    }

    private fun initView(){
        binding.frResultIdTv.text = Assignment2.preferences.getEmail("")
        binding.frResultPwdTv.text = Assignment2.preferences.getPassword("")
    }

    private fun setClickListener(){
        binding.frResultToolbar.toolbarBackBtn.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_emailFragment)
        }
    }
}