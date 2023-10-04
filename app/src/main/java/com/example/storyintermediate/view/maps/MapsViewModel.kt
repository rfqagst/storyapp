package com.example.storyintermediate.view.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyintermediate.api.response.StoryResponse
import com.example.storyintermediate.data.repo.MapsRepo
import kotlinx.coroutines.launch

class MapsViewModel(private val repo: MapsRepo) : ViewModel() {

    private val _storyData = MutableLiveData<StoryResponse>()
    val storyData: LiveData<StoryResponse> get() = _storyData

    fun getStoryLocation() {
        viewModelScope.launch {
            try {
                val response = repo.getStoryLocation()
                val location = response.listStory.firstOrNull()
                _storyData.value = response
                if (location != null) {
                    Log.d(
                        "MapsViewModel",
                        "lat nya adalah ${location.lat} dan lon nya ${location.lon}"
                    )
                } else {
                    Log.d("MapsViewModel", "Gagal dapat lokasi")
                }

            } catch (e: Exception) {
                Log.d("MapsViewModel", "Gagal dapat Location")
            }
        }
    }
}