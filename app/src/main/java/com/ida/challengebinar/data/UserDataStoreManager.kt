package com.ida.challengebinar.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ida.challengebinar.data.room.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStoreManager(private val context: Context) {
    companion object {
        private const val DATASTORE_NAME = "user_preferences"
        private val ID_USER_KEY = intPreferencesKey("ID_USER_KEY")
        private val USERNAME_KEY = stringPreferencesKey("USERNAME_KEY")
        private val EMAIL_KEY = stringPreferencesKey("EMAIL_KEY")
        private val PASSWORD_KEY = stringPreferencesKey("PASSWORD_KEY")
        private val Context.userDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )

        const val DEFAULT_ID = -1
        const val DEFAULT_USERNAME = "DEF_USERNAME"
        const val DEFAULT_EMAIL = "DEF_EMAIL"
        const val DEFAULT_PASSWORD = "DEF_PASSWORD"
    }


    suspend fun saveUserPref(user: UserEntity) {
        context.userDataStore.edit {
            it[ID_USER_KEY] = user.id!!.toInt()
            it[USERNAME_KEY] = user.username
            it[EMAIL_KEY] = user.email
            it[PASSWORD_KEY] = user.password
        }
    }

    fun getUserPref(): Flow<UserEntity> {
        return context.userDataStore.data.map {
            UserEntity(
                it[ID_USER_KEY] ?: DEFAULT_ID,
                it[USERNAME_KEY] ?: DEFAULT_USERNAME,
                it[EMAIL_KEY] ?: DEFAULT_EMAIL,
                it[PASSWORD_KEY] ?: DEFAULT_PASSWORD,
                null
            )
        }
    }

    suspend fun deleteUserFromPref() {
        context.userDataStore.edit {
            it.clear()
        }
    }

}