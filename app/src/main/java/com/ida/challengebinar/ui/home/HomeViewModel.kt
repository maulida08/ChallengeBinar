package com.ida.challengebinar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ida.challengebinar.data.Repository
import com.ida.challengebinar.data.room.UserEntity
import com.ida.challengebinar.data.service.Resource
import com.ida.challengebinar.room.GetAllMoviePopular
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val  _getDataUser : MutableLiveData<UserEntity> = MutableLiveData()
    val getDataUser : LiveData<UserEntity> get() = _getDataUser

    private val _userDataStore: MutableLiveData<UserEntity> = MutableLiveData()
    val userDataStore: LiveData<UserEntity> get() = _userDataStore

    private val _popularMovie: MutableLiveData<Resource<GetAllMoviePopular>> = MutableLiveData()
    val popularMovie: LiveData<Resource<GetAllMoviePopular>> get() = _popularMovie

    fun getDataStore() {
        viewModelScope.launch {
            repository.getUserPref().collect {
                _userDataStore.value = it
            }
        }
    }

    fun getUser(username: String) {
        viewModelScope.launch {
            _getDataUser.value = repository.getUser(username)
        }
    }

    fun getPopularMovie() {
        viewModelScope.launch {
            _popularMovie.postValue(Resource.loading())
            try {
                _popularMovie.postValue(Resource.success(repository.getAllMoviePopular()))
            }catch (exception:Exception){
                _popularMovie.postValue(Resource.error(exception.localizedMessage?:"Error occured"))
            }
        }
    }

    fun deleteUserFromPref() {
        viewModelScope.launch {
            repository.deleteUserFromPref()
        }
    }

}