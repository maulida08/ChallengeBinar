package com.ida.challengebinar.ui.login

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
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val  _login : MutableLiveData<UserEntity> = MutableLiveData()
    val login : LiveData<UserEntity> get() = _login

    private val _userDataStore: MutableLiveData<UserEntity> = MutableLiveData()
    val userDataStore: LiveData<UserEntity> get() = _userDataStore

    fun getDataStore() {
        viewModelScope.launch {
            repository.getUserPref().collect {
                _userDataStore.value = it
            }
        }
    }

    fun saveDataStore(user: UserEntity) {
        viewModelScope.launch {
            repository.saveUserPref(user)
        }
    }

    fun getUser(username: String) {
        viewModelScope.launch {
            _login.value = repository.getUser(username)
        }
    }
}