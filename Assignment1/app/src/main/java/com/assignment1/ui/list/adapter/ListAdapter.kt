package com.assignment1.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment1.data.database.User
import com.assignment1.databinding.ItemUserIlstBinding
import com.google.android.material.snackbar.Snackbar

class ListAdapter(
    val onClickDelete:(user: User)-> Unit,
    val onClickEdit:(user: User)-> Unit
    ): ListAdapter<User,com.assignment1.ui.list.adapter.ListAdapter.ViewHolder>(UserDiffCallback()){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val layoutInflater = LayoutInflater.from(parent.context)
         val binding = ItemUserIlstBinding.inflate(layoutInflater, parent, false)
         return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

     inner class ViewHolder(val binding: ItemUserIlstBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: User,pos: Int) {
            binding.user = item
            binding.executePendingBindings()
            binding.itemUserListDeleteBtn.setOnClickListener {
                onClickDelete(item)
                removeItem(pos)
            }
            binding.itemUserListEditBtn.setOnClickListener {
                onClickEdit(item)
            }
        }
    }

    fun removeItem(pos: Int){
        val newList = mutableListOf<User>()
        newList.addAll(currentList)
        if(pos < newList.size)newList.removeAt(pos)
        submitList(newList)
    }

}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id && oldItem.name== newItem.name && oldItem.phone ==newItem.phone
    }
    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }
}
