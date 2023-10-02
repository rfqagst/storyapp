package com.example.storyintermediate.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyintermediate.data.repo.StoryRepo
import com.example.storyintermediate.di.Injection
import com.example.storyintermediate.view.detail.DetailViewModel
import com.example.storyintermediate.view.maps.MapsViewModel
import com.example.storyintermediate.view.story.StoryViewModel
import com.example.storyintermediate.view.story.addstory.AddStoryViewModel

class StoryModelFactory(private val repo: StoryRepo) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(repo) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repo) as T
            }

            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(repo) as T
            }

            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repo) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StoryModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): StoryModelFactory {
            return INSTANCE ?: synchronized(StoryModelFactory::class.java) {
                INSTANCE ?: StoryModelFactory(Injection.provideStoryRepository(context)).also {
                    INSTANCE = it
                }
            }
        }
    }
}