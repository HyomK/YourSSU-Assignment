package com.assignment2.ui

import androidx.lifecycle.ViewModel
import com.assignment2.data.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel@Inject constructor(private val repository: AccountRepository): ViewModel() {
    // TODO: Implement the ViewModel
    val account = repository.getAccount()
}