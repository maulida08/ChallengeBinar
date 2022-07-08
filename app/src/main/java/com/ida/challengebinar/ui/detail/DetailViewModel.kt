package com.ida.challengebinar.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ida.challengebinar.data.Repository
import com.ida.challengebinar.data.model.GetDetailMovie
import com.ida.challengebinar.data.service.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.Call
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _detailMovie: MutableLiveData<Resource<GetDetailMovie>> = MutableLiveData()
    val detailMovie: LiveData<Resource<GetDetailMovie>> get() = _detailMovie

    fun getAllDetail(id: Int) {
        viewModelScope.launch {
            _detailMovie.postValue(Resource.loading())
            try {
                _detailMovie.postValue(Resource.success(repository.getALLDetail(id)))
            }catch (exception:Exception){
                _detailMovie.postValue(Resource.error(exception.localizedMessage?:"Error occured"))
            }
        }
    }
}