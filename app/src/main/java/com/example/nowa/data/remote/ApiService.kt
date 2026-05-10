package com.example.kelompok_nokonteks_tam_nowa.data.remote

import com.example.kelompok_nokonteks_tam_nowa.data.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
