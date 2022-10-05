package com.assignment1.ui.list

import android.app.Application
import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.assignment1.data.database.User
import com.assignment1.data.database.UserDatabase
import com.assignment1.data.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ListViewModel(application: Application): AndroidViewModel(application) {

    private var repository: UserRepository
    private val context = application.applicationContext
    init {
        repository =
            UserRepository(UserDatabase.getInstance(application.applicationContext).userDao())

    }



    fun getAll(): LiveData<List<User>> = repository.getUsers()

    private fun loadList() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getUsers()
            Log.e("view-model","${data.value}")
//            withContext(Dispatchers.Main) {
//                userList.value = data
//            }
        }
    }

    fun getUserList() = repository.getUsers()

}




