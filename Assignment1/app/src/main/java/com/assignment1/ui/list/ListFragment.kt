package com.assignment1.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment1.MainActivity
import com.assignment1.R
import com.assignment1.data.database.UserDatabase
import com.assignment1.data.repository.UserRepository
import com.assignment1.databinding.FragmentListBinding
import com.assignment1.ui.register.RegisterViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListFragment : Fragment(){
    private var _binding : FragmentListBinding? = null

    private val binding get() = _binding!!
//    private val viewModel by  lazy {
//        val repository = UserRepository(UserDatabase.getInstance(activity as MainActivity).userDao()!!)
//        ViewModelProvider(this, ListViewModelFactory(repository))[ListViewModel::class.java]
//    }
    private val viewModel by viewModels<ListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner =viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userAdapter = ListAdapter()
        binding.frListRv. adapter = userAdapter
        viewModel.getUserList().observe(viewLifecycleOwner, Observer {
            it?.let{
                userAdapter.submitList(it)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}