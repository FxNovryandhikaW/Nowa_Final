package com.example.nowa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kelompok_nokonteks_tam_nowa.data.model.Post
import com.example.kelompok_nokonteks_tam_nowa.data.remote.RetrofitClient
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    var posts: List<Post> by mutableStateOf(listOf())
        private set

    var isLoading: Boolean by mutableStateOf(false)
        private set

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            isLoading = true
            try {
                posts = RetrofitClient.apiService.getPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}
