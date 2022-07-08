package com.ida.challengebinar.ui.register

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
class RegisterViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val  _register : MutableLiveData<Long> = MutableLiveData()
    val register : LiveData<Long> get() = _register

    fun addUser(user: UserEntity){
        viewModelScope.launch {
            _register.value = repository.addUser(user)
        }
    }
}