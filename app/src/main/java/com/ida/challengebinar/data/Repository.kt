package com.ida.challengebinar.data

import com.ida.challengebinar.data.room.DbHelper
import com.ida.challengebinar.data.room.UserEntity
import com.ida.challengebinar.data.service.ApiHelper
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiHelper: ApiHelper,
    private val dbHelper: DbHelper,
    private val userDataStore: UserDataStoreManager
) {

    // Api
    suspend fun getAllMoviePopular() = apiHelper.getAllMoviePopular()

    suspend fun getALLDetail(movie_id: Int) = apiHelper.getALLDetail(movie_id)

    // DataStore
    suspend fun saveUserPref(user: UserEntity) {
        userDataStore.saveUserPref(user)
    }

    fun getUserPref(): Flow<UserEntity> {
        return userDataStore.getUserPref()
    }

    suspend fun deleteUserFromPref() {
        userDataStore.deleteUserFromPref()
    }

    // Room
    suspend fun addUser(user: UserEntity): Long = dbHelper.addUser(user)

    suspend fun getUser(username: String): UserEntity {
        return dbHelper.getUser(username)
    }

    suspend fun updateUser(user: UserEntity): Int = dbHelper.updateUser(user)

}