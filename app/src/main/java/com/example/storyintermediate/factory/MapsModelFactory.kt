package com.example.storyintermediate.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyintermediate.data.repo.MapsRepo
import com.example.storyintermediate.di.Injection
import com.example.storyintermediate.view.maps.MapsViewModel
import com.example.storyintermediate.view.story.StoryViewModel

class MapsModelFactory(private val repo: MapsRepo) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MapsModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): MapsModelFactory {
            return INSTANCE ?: synchronized(MapsModelFactory::class.java) {
                INSTANCE ?: MapsModelFactory(Injection.provideMapsRepository(context)).also {
                    INSTANCE = it
                }
            }
        }
    }
}