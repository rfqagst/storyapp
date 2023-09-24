package com.example.storyintermediate.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyintermediate.api.response.DetailResponse
import com.example.storyintermediate.data.StoryRepo
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: StoryRepo) : ViewModel() {
    private val _detailStory = MutableLiveData<DetailResponse>()
    val detailStory: LiveData<DetailResponse> = _detailStory

    fun getStoryDetail (id : String) {
        viewModelScope.launch {
            try {
                val response = repo.getStoryDetail(id)
                _detailStory.value = response
                Log.d("DetailViewModel", "id nya adalah $id")
            } catch (e: Exception) {
                Log.d("DetailViewModel", "id nya adalah $id")
                Log.d("DetailViewModel", "Gagal dapat detail story: ${e.message}")
            }
        }
    }


}