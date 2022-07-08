package com.ida.challengebinar.di

import android.content.Context
import androidx.room.Room
import com.ida.challengebinar.data.room.AppDatabase
import com.ida.challengebinar.data.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideUserDAO(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

}