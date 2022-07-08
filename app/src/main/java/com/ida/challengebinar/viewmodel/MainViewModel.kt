package com.ida.challengebinar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ida.challengebinar.data.UserDataStoreManager
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserDataStoreManager) : ViewModel() {

    fun saveDataStore(value: String) {
        viewModelScope.launch {
            pref.saveUserPref(value)
        }
    }

    fun getDataStore(): LiveData<String> {
        return pref.getUserPref().asLiveData()
    }
}