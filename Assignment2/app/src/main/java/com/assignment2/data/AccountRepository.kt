package com.assignment2.data

import com.assignment2.Assignment2.Companion.preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class AccountRepository @Inject constructor() {
    fun setEmail(email: String){
        preferences.setEmail(email)
    }

    fun setPassword(pwd : String){
        preferences.setPassword(pwd)
    }

    fun getAccount(): Account{
       return preferences.getAccount()!!
    }
}


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun bindAccountRepository(): AccountRepository = AccountRepository()
}
