package com.ida.challengebinar.data.service

class ApiHelper(private val apiService: ApiService) {
    suspend fun getAllMoviePopular() = apiService.getAllMoviePopular()

    suspend fun getALLDetail(movie_id: Int) = apiService.getALLDetail(movie_id)
}