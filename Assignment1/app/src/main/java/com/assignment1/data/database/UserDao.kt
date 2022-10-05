package com.assignment1.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table WHERE id = :id")
    fun deleteUser(vararg id: Int)

    @Query("DELETE FROM user_table")
     fun deleteAll()

    @Query("SELECT * FROM user_table")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE id = :id")
    fun findUserWithId(vararg id : Int): User

    @Query("SELECT * FROM user_table WHERE name LIKE :search")
    fun findUserWithName(vararg search: String): List<User>

    @Query("SELECT * FROM user_table WHERE phone LIKE :search")
    fun findUserWithPhone(vararg search: String): List<User>

}