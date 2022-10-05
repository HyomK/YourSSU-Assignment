package com.assignment1.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import com.assignment1.MainActivity
import com.assignment1.R
import com.assignment1.data.database.User
import com.assignment1.databinding.FragmentListBinding
import com.assignment1.ui.list.adapter.ItemDecoration
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListFragment : Fragment(){
    private var _binding : FragmentListBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<ListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userAdapter = ListAdapter(::onClickDeleteItem)

        val swipeHelperCallback = SwipeHelperCallback().apply {
            setClamp(230f)
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.frListRv)

        binding.frListRv.apply{
            adapter = userAdapter
            setOnTouchListener { _, _ ->
                swipeHelperCallback.removePreviousClamp(this)
                false
            }
            addItemDecoration(ItemDecoration())
        }
        viewModel.uiState.flowWithLifecycle(lifecycle)
            .onEach {
                binding.uiState=it
                userAdapter.submitList(it.result)
            }
            .launchIn(lifecycleScope)

        viewModel.getUserList().observe(viewLifecycleOwner, Observer {
            it?.let{ userAdapter.submitList(it) }
        })
    }

    private fun onClickDeleteItem (user : User){
        viewModel.deleteUser(user)
        Toast.makeText( (activity as MainActivity),"삭제되었습니다",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
    }
}