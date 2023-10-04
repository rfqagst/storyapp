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
