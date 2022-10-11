package com.assignment1.ui.list

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.assignment1.RegexConstant
import com.assignment1.data.database.User
import com.assignment1.data.database.UserDatabase
import com.assignment1.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditDialogViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
    ): AndroidViewModel(application) {
    private var repository: UserRepository

    private val _uiState = MutableStateFlow(createState())
    val uiState get() = _uiState.asStateFlow()

    init {
        repository =
            UserRepository(UserDatabase.getInstance(application.applicationContext).userDao())
    }

    private fun createState() : EditUiState{
        savedStateHandle.get<User>("user")?.let {
            return EditUiState(it,it.name,it.phone,onNameChanged = ::handleNameChanged, onPhoneChanged = ::handlePhoneChanged )
        }
        return EditUiState(onNameChanged = ::handleNameChanged, onPhoneChanged = ::handlePhoneChanged )
    }


    fun updateUser(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(uiState.value.user!!.copy(name = uiState.value.name, phone = uiState.value.phone ))
        }
    }

    private fun handleNameChanged(text: Editable?) {
        _uiState.update { it.copy(name = text.toString()) }
    }

    private fun handlePhoneChanged(text: Editable?) {
        _uiState.update { it.copy(phone = text.toString()) }
    }


    data class EditUiState(
        var user: User?=null,
        var name: String ="",
        var phone: String ="",
        val onNameChanged: (Editable?) -> Unit,
        val onPhoneChanged: (Editable?) -> Unit,
    ) {
        val isEnabled = name.isNotBlank()&& RegexConstant.PHONE_REGEX.matches(phone)
    }
}