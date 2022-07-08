package com.ida.challengebinar.di

import com.ida.challengebinar.data.Repository
import com.ida.challengebinar.data.UserDataStoreManager
import com.ida.challengebinar.data.room.DbHelper
import com.ida.challengebinar.data.service.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @ViewModelScoped
    @Provides
    fun provideRepository(
        apiHelper: ApiHelper,
        dbHelper: DbHelper,
        userDataStore: UserDataStoreManager
    ) = Repository(apiHelper,dbHelper,userDataStore)
}