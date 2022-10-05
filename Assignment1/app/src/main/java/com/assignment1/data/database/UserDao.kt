package com.assignment1.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table WHERE id = :id")
    suspend fun deleteUser(vararg id: Int)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_table")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM user_table")
    fun getAllByList(): List<User>

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun findUserWithId(vararg id : Int): User

    @Query("SELECT * FROM user_table WHERE name LIKE :search")
    suspend fun findUserWithName(vararg search: String): List<User>

    @Query("SELECT * FROM user_table WHERE phone LIKE :search")
    suspend fun findUserWithPhone(vararg search: String): List<User>

}