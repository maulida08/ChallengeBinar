package com.ida.challengebinar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ida.challengebinar.data.service.ApiClient
import com.ida.challengebinar.room.GetAllMoviePopular
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    val popularMovie: MutableLiveData<GetAllMoviePopular> = MutableLiveData()

    fun getPopularMovie() {
        ApiClient.instance.getAllMoviePopular().enqueue(object : Callback<GetAllMoviePopular> {
            override fun onResponse(
                call: Call<GetAllMoviePopular>,
                response: Response<GetAllMoviePopular>
            ) {
                response.body()?.let {
                    popularMovie.postValue(it)
                }
            }

            override fun onFailure(call: Call<GetAllMoviePopular>, t: Throwable) {
                Log.d("PopularViewModel", "${t.message}")
            }

        })
    }
}