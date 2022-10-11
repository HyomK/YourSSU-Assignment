package com.assignment1.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.assignment1.data.database.User
import com.assignment1.data.database.UserDao
import kotlinx.coroutines.*

class UserRepository(private val userDataSource : UserDao) {

    val readAllData: LiveData<List<User>> = userDataSource.getAll()


    suspend fun addUser(name: String,phone: String){

        if(readAllData.value.isNullOrEmpty())
            return userDataSource.insert(User(0,name,phone))
        else readAllData.value?.let {
            it.forEach { user->  Log.e("repository","${user}")  }
            return userDataSource.insert(User(it.size,name,phone))
        }
    }

    suspend fun clear(){
        userDataSource.deleteAll()
    }

    suspend fun deleteUser(id : Int){
        userDataSource.deleteUser(id)
    }

    fun getUsers(): LiveData<List<User>>{
        return userDataSource.getAll()
    }

    fun list() : List<User>{
        return userDataSource.getAllByList()
    }

    suspend fun getUser(id : Int):User{
        return userDataSource.findUserWithId(id)
    }

    suspend fun searchUser(search : String) :List<User> {
        return userDataSource.findUser(search)
    }

    suspend fun updateUser(user : User) = userDataSource.update(user)

}