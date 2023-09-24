package com.example.storyintermediate.view.story.addstory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyintermediate.data.StoryRepo
import kotlinx.coroutines.launch
import java.io.File


class AddStoryViewModel(private val repo: StoryRepo) : ViewModel() {
    fun postStory(file: File, description: String) = repo.postStory(file, description)
}
