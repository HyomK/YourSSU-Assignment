package com.assignment1.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.assignment1.MainActivity
import com.assignment1.R
import com.assignment1.databinding.FragmentRegisterBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null

    private val binding get() = _binding!!

    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = registerViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
    }

    private fun setObserver(){
        registerViewModel.viewEvents.
            flowWithLifecycle(lifecycle)
            .onEach(::handleEvents)
            .launchIn(lifecycleScope)
    }

    private fun handleEvents(event : List<RegisterViewEvent>){
        event.firstOrNull()?.let{
            event ->
            when(event){
                RegisterViewEvent.Success->{
                    Toast.makeText((context as MainActivity),"등록되었습니다",Toast.LENGTH_SHORT).show()
                }
                RegisterViewEvent.Failure->{
                    Toast.makeText((context as MainActivity),"등록에 실패했습니다",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}