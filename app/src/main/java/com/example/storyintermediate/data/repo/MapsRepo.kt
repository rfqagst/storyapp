package com.example.storyintermediate.data.repo

import com.example.storyintermediate.api.response.StoryResponse
import com.example.storyintermediate.api.retrofit.ApiService

class MapsRepo(private val apiService: ApiService)  {
    suspend fun getStoryLocation(): StoryResponse {
        val response = apiService.getStoriesWithLocation()
        return response
    }

    companion object {
        @Volatile
        private var instance: MapsRepo? = null
        fun getInstance(
            apiService: ApiService
        ): MapsRepo =
            instance ?: synchronized(this) {
                instance ?: MapsRepo(apiService)
            }.also { instance = it }
    }
}