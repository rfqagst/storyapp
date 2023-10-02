package com.example.storyintermediate.view.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyintermediate.api.response.ListStoryItem
import com.example.storyintermediate.data.repo.StoryRepo

class StoryViewModel(private val repo: StoryRepo) :
    ViewModel() {

    private val _storyPagingData = MutableLiveData<PagingData<ListStoryItem>>()
    val storyPagingData: LiveData<PagingData<ListStoryItem>> =
        repo.getStoriesMediator().cachedIn(viewModelScope)
}
//
//    private val _storyData = MutableLiveData<StoryResponse>()
//    val storyData: LiveData<StoryResponse> get() = _storyData

//    fun getStories() {
//        viewModelScope.launch {
//            try {
//                val response = repo.getStories()
//                val firstStory = response.listStory.firstOrNull()
//                _storyData.value = response
//                if (firstStory != null) {
//                    Log.d(
//                        "StoryViewModel",
//                        "Detail cerita pertama dari API: Judul: ${firstStory.name}, Deskripsi: ${firstStory.description}, URL Gambar: ${firstStory.photoUrl}"
//                    )
//                } else {
//                    Log.d("StoryViewModel", "Tidak ada cerita yang didapatkan dari API")
//                }
//            } catch (e: Exception) {
//                Log.d("StoryViewModel", "Gagal dapat story")
//            }
//        }
//    }
