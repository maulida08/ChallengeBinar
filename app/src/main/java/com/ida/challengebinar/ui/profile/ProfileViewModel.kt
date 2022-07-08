package com.ida.challengebinar.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ida.challengebinar.data.Repository
import com.ida.challengebinar.data.room.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _update : MutableLiveData<Int> = MutableLiveData()
    val update : LiveData<Int> get() = _update

    fun deleteUserFromPref() {
        viewModelScope.launch {
            repository.deleteUserFromPref()
        }
    }

    fun updateUser(user: UserEntity){
        viewModelScope.launch {
            _update.value = repository.updateUser(user)
        }
    }
}