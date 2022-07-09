package com.ida.challengebinar.data.room

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT * FROM UserEntity WHERE username = :username and password = :password)")
    suspend fun checkUser(username: String, password: String) : Boolean

    @Query("SELECT * FROM UserEntity WHERE username like :username")
    suspend fun getUser(username: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userEntity: UserEntity): Long
    
    @Update
    suspend fun updateUser(userEntity: UserEntity): Int
}