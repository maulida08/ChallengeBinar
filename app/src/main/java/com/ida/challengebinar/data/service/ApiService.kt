package com.ida.challengebinar.data.service

import com.ida.challengebinar.data.model.GetDetailMovie
import com.ida.challengebinar.room.GetAllMoviePopular
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("movie/popular?api_key=7fd85d1ae16130aa2bbe3d705027b5be")
    suspend fun getAllMoviePopular() : GetAllMoviePopular

    @GET("movie/{movie_id}?api_key=7fd85d1ae16130aa2bbe3d705027b5be")
    suspend fun getALLDetail(@Path("movie_id") movie_id: Int) : GetDetailMovie


}