package com.example.storyintermediate.view.story

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyintermediate.api.response.StoryResponse
import com.example.storyintermediate.data.StoryRepo
import com.example.storyintermediate.data.pref.UserPreference
import kotlinx.coroutines.launch

class StoryViewModel(private val repo: StoryRepo) :
    ViewModel() {
    private val _storyData = MutableLiveData<StoryResponse>()
    val storyData: LiveData<StoryResponse> get() = _storyData

    private val STORIES_KEY = stringPreferencesKey("stories")


    fun getStories() {
        viewModelScope.launch {
            try {
                val response = repo.getStories()
                val firstStory = response.listStory.firstOrNull()
                _storyData.value = response
                if (firstStory != null) {
                    Log.d("StoryViewModel", "Detail cerita pertama dari API: Judul: ${firstStory.name}, Deskripsi: ${firstStory.description}, URL Gambar: ${firstStory.photoUrl}")
                } else {
                    Log.d("StoryViewModel", "Tidak ada cerita yang didapatkan dari API")
                }
            } catch (e: Exception) {
                Log.d("StoryViewModel", "Gagal dapat story")
            }
        }
    }
}