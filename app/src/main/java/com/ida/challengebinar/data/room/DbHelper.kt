package com.ida.challengebinar.data.room

import javax.inject.Inject

class DbHelper @Inject constructor(private val userDao: UserDao) {
    suspend fun addUser(user: UserEntity): Long = userDao.addUser(user)
    suspend fun getUser(username: String): UserEntity = userDao.getUser(username)
    suspend fun updateUser(user: UserEntity):Int = userDao.updateUser(user)
}