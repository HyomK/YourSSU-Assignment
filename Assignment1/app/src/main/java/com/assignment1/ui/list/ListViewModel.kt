package com.assignment1.ui.list

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.*
import com.assignment1.data.database.User
import com.assignment1.data.database.UserDatabase
import com.assignment1.data.repository.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ListViewModel(application: Application): AndroidViewModel(application) {

    private var repository: UserRepository

    private val _uiState = MutableStateFlow(ListUiState(onSearchChanged = ::handleSearchChanged))
    val uiState get() = _uiState.asStateFlow()

    init {
        repository =
            UserRepository(UserDatabase.getInstance(application.applicationContext).userDao())
    }

    fun getUserList(): LiveData<List<User>> {
        repository.getUsers().let { res ->
            return res
        }
    }

    private var searchValidationJob : Job? = null
    fun handleSearchChanged(text: Editable?) {
        searchValidationJob?.cancel()

        searchValidationJob = viewModelScope.launch(Dispatchers.IO) {
            delay(300L)
            if(text.isNullOrBlank()){
                _uiState.update { it.copy(search="",result = repository.list()) }
                return@launch
            }
            val res = repository.searchUser("%${text}%")
            _uiState.update { it.copy(result = res, search = text.toString()) }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user.id)
        }
    }

    fun initSearchBar() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(search = "", result = repository.list()) }
        }
    }

}

data class ListUiState(
    val search: String ="",
    val onSearchChanged: (Editable?) -> Unit,
    var result: List<User> = emptyList<User>()
) {
   val isEmptyState = search.isNotBlank()&&result.isNullOrEmpty()
}




