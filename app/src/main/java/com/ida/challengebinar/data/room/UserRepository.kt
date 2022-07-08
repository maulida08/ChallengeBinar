package com.ida.challengebinar.data.room

import android.content.Context

class UserRepository(context: Context) {
    private val mDb = AppDatabase.getInstance(context)

    fun checkUser(username: String, password: String): Boolean {
        return mDb!!.userDao().checkUser(username, password)
    }

    fun getUser(username: String): UserEntity {
        return mDb!!.userDao().getUser(username)
    }

    fun addUser(userEntity: UserEntity): Long {
        return mDb!!.userDao().addUser(userEntity)
    }

    fun updateUser(userEntity: UserEntity): Int {
        return mDb!!.userDao().updateUser(userEntity)
    }
}