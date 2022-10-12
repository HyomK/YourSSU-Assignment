package com.assignment1.ui.register

import android.app.Application
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.util.Log
import androidx.lifecycle.*
import com.assignment1.RegexConstant
import com.assignment1.data.database.User
import com.assignment1.data.database.UserDatabase
import com.assignment1.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var repository : UserRepository

    private val _viewEvents: MutableStateFlow<List<RegisterViewEvent>> = MutableStateFlow(emptyList())
    val viewEvents get() = _viewEvents.asStateFlow()

    private val _uiState = MutableStateFlow(createState())
    val uiState get() = _uiState.asStateFlow()

    init {
        repository = UserRepository(UserDatabase.getInstance(application.applicationContext)!!.userDao())
    }

    private fun createState() : RegisterUiState{
        return RegisterUiState(
            name = "",
            phone = "",
            onNameChanged = ::handleNameChanged,
            onPhoneChanged = ::handlePhoneChanged,
            onSubmit = ::addUser
        )
    }


     private fun addUser(){
        CoroutineScope(Dispatchers.Default).launch {
            if(!uiState.value.errorReason.isNullOrBlank()){
                _viewEvents.update{ it + RegisterViewEvent.Failure(
                    uiState.value.errorReason!!) }
                return@launch
            }
            try{
                repository.addUser(uiState.value.name, uiState.value.phone)
                _uiState.update { it.copy(name="", phone = "") }
                _viewEvents.update{ it + RegisterViewEvent.Success }

            }catch(error: Throwable){
                _viewEvents.update{ it + RegisterViewEvent.Failure("등록 실패!") }
                return@launch
            }
        }
    }


    private fun handleNameChanged(text: Editable?) {
        _uiState.update { it.copy(name = text.toString()) }
    }

    private fun handlePhoneChanged(text: Editable?) {
        _uiState.update { it.copy(phone = text.toString()) }
    }

    fun consumeEvent(viewEvent: RegisterViewEvent) {
        _viewEvents.update{it - viewEvent}
    }

}

sealed class RegisterViewEvent {
    object Success : RegisterViewEvent()
    data class Failure(val msg : String) : RegisterViewEvent()
}

data class RegisterUiState(
    val name: String ="",
    val phone: String= "",
    val onNameChanged: (Editable?) -> Unit,
    val onPhoneChanged: (Editable?) -> Unit,
    val onSubmit: () -> Unit,
) {
    val errorReason =
        if (!name.isNullOrEmpty() && RegexConstant.PHONE_REGEX.matches(phone)) null
        else if(name.isNullOrEmpty()) "이름을 입력해 주세요."
        else "올바른 휴대폰 번호를 입력해주세요."
    val isEnabled = !name.isNullOrEmpty() && phone.length==13
}