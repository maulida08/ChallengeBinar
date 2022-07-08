package com.ida.challengebinar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ida.challengebinar.data.model.GetDetailMovie
import com.ida.challengebinar.data.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    val detailMovie: MutableLiveData<GetDetailMovie> = MutableLiveData()

    fun getAllDetail(id: Int) {
        ApiClient.instance.getALLDetail(id).enqueue(object : Callback<GetDetailMovie> {
            override fun onResponse(
                call: Call<GetDetailMovie>,
                response: Response<GetDetailMovie>
            ) {
                detailMovie.postValue(response.body())
            }

            override fun onFailure(call: Call<GetDetailMovie>, t: Throwable) {
                Log.d("DetailViewModel", "${t.message}")
            }

        })
    }
}