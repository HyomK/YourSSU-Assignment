package com.assignment2.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.assignment2.Assignment2
import com.assignment2.R
import com.assignment2.databinding.FragmentEmailBinding
import com.assignment2.databinding.FragmentPasswordBinding
import com.assignment2.util.PreferenceManager

class PasswordFragment : Fragment() {
    private var _binding : FragmentPasswordBinding? = null

    private val binding get() = _binding!!

    private val viewModel by activityViewModels<PasswordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.frPwdInputField.getEditText?.addTextChangedListener {
            viewModel.handlePassword(it)
        }
        binding.frPwdNextBtn.setOnClickListener {
            Assignment2.preferences.setPassword(viewModel.uistate.value!!.input!!)
            findNavController().navigate(R.id.action_passwordFragment_to_resultFragment)
        }
    }

}